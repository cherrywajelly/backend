package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.enums.premium.PremiumType;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.payment.Payment;
import com.timeToast.timeToast.domain.premium.Premium;
import com.timeToast.timeToast.dto.icon.response.CreatorIconGroupResponse;
import com.timeToast.timeToast.dto.member.member.response.CreatorResponses;
import com.timeToast.timeToast.dto.member.member.request.CreatorAccount;
import com.timeToast.timeToast.dto.member.member.response.*;
import com.timeToast.timeToast.dto.premium.response.MemberPremium;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.exception.ConflictException;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.global.util.StringValidator;
import com.timeToast.timeToast.repository.follow.FollowRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.payment.PaymentRepository;
import com.timeToast.timeToast.repository.premium.PremiumRepository;
import com.timeToast.timeToast.service.icon.AdminIconService;
import com.timeToast.timeToast.service.image.FileUploadService;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.*;
import static com.timeToast.timeToast.global.constant.FileConstant.*;
import static com.timeToast.timeToast.global.constant.SuccessConstant.VALID_NICKNAME;

import java.time.LocalDate;
import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;
    private final PremiumRepository premiumRepository;
    private final PaymentRepository paymentRepository;
    private final FileUploadService fileUploadService;
    private final AdminIconService adminIconService;

    public MemberServiceImpl(final MemberRepository memberRepository, final FollowRepository followRepository,
                             final PremiumRepository premiumRepository, final PaymentRepository paymentRepository,
                             final FileUploadService fileUploadService, final AdminIconService adminIconService) {

        this.memberRepository = memberRepository;
        this.followRepository = followRepository;
        this.premiumRepository = premiumRepository;
        this.paymentRepository = paymentRepository;
        this.fileUploadService = fileUploadService;
        this.adminIconService = adminIconService;
    }

    @Value("${spring.cloud.oci.base-url}")
    private String baseUrl;

    @Transactional
    @Override
    public MemberInfoResponse saveToStaff(final long memberId) {
        Member member = updateRole(memberId, MemberRole.STAFF);
        return getMemberInfo(member.getId());
    }

    @Transactional
    @Override
    public MemberInfoResponse saveToCreators(final long memberId) {
        Member member = updateRole(memberId, MemberRole.CREATOR);
        return getMemberInfo(member.getId());
    }

    @Transactional
    @Override
    public MemberInfoResponse saveToUser(final long memberId) {
        Member member = updateRole(memberId, MemberRole.USER);
        return getMemberInfo(member.getId());
    }


    private Member updateRole(final long memberId, final MemberRole role) {
        Member member = memberRepository.getById(memberId);
        member.updateMemberRole(role);
        return member;
    }

    @Transactional
    @Override
    public MemberInfoResponse saveProfileImage(final long memberId, final MultipartFile profileImage) {
        Member member = memberRepository.getById(memberId);

        String url = baseUrl + MEMBER.value() + SLASH.value() + IMAGE.value() + SLASH.value() + memberId;
        String profileImageUrl = fileUploadService.uploadfile(profileImage,url);

        member.updateProfileUrl(profileImageUrl);

        MemberPremium memberPremium = getMemberPremiumByMember(member);
        return MemberInfoResponse.from(member, memberPremium);
    }

    @Transactional(readOnly = true)
    @Override
    public Response nicknameValidation(final String nickname) {
        nicknameCheck(nickname);
        return new Response(StatusCode.OK.getStatusCode(), VALID_NICKNAME.getMessage());
    }

    private void nicknameCheck(final String nickname) {
        if(memberRepository.existsByNickname(nickname)){
            throw new ConflictException(NICKNAME_CONFLICT.getMessage());
        }

        if(!StringValidator.nicknameValidation(nickname)){
            throw new BadRequestException(INVALID_NICKNAME.getMessage());
        }
    }

    @Transactional
    @Override
    public MemberInfoResponse saveNickname(final String nickname, final long memberId){
        Member member = memberRepository.getById(memberId);
        updateNicknameByMember(member, nickname);
        MemberPremium memberPremium = getMemberPremiumByMember(member);
        return MemberInfoResponse.from(member, memberPremium);
    }

    private void updateNicknameByMember(final Member member, final String nickname) {
        nicknameCheck(nickname);
        member.updateNickname(nickname);
    }

    @Transactional
    @Override
    public CreatorInfoResponse saveCreatorInfo(final long creatorId, final CreatorAccount creatorAccount) {
        Member creator = memberRepository.getById(creatorId);
        updateCreatorAccount(creator, creatorAccount);
        return CreatorInfoResponse.from(creator);
    }

    private void updateCreatorAccount(final Member creator, final CreatorAccount creatorAccount) {
        creator.updateAccount(creatorAccount.bank(), creatorAccount.accountNumber());
    }

    @Transactional(readOnly = true)
    @Override
    public MemberInfoResponse getMemberInfo(final long memberId) {
        Member member = memberRepository.getById(memberId);
        MemberPremium memberPremium = getMemberPremiumByMember(member);
        return MemberInfoResponse.from(member, memberPremium);
    }

    @Transactional(readOnly = true)
    @Override
    public MemberPremium getMemberPremiumByMember(final Member member) {
       Premium premium = premiumRepository.getById(member.getPremiumId());

        LocalDate expiredDate = null;
        if(premium.getPremiumType().equals(PremiumType.PREMIUM)){
            Optional<Payment> payment = paymentRepository.findRecentPremiumByMemberId(member.getId());
            if(payment.isPresent()){
                expiredDate = payment.get().getExpiredDate();
            }
        }

        return MemberPremium.from(premium,expiredDate);
    }

    @Transactional(readOnly = true)
    @Override
    public MemberProfileResponse getMemberProfile(final long memberId) {
        return getMemberProfile(memberId, memberId);
    }

    @Transactional(readOnly = true)
    @Override
    public MemberProfileResponse getMemberProfile(final long loginId, final long memberId) {
        Member member = memberRepository.getById(memberId);
        boolean isFollow = followRepository.findByFollowingIdAndFollowerId(memberId, loginId).isPresent();

        return MemberProfileResponse.from(member, isFollow);
    }

    @Transactional(readOnly = true)
    @Override
    public CreatorResponses getCreators() {
        List<CreatorResponse> creatorResponses = memberRepository.findAllByMemberRole(MemberRole.CREATOR).stream()
                .sorted(Comparator.comparing(Member::getNickname))
                .map(member -> {
                    CreatorIconGroupResponse creatorIconGroupResponse = adminIconService.getCreatorIconGroups(member.getId());
                    return new CreatorResponse(CreatorInfoResponse.from(member), creatorIconGroupResponse);
                })
                .toList();
        return new CreatorResponses(creatorResponses);
    }

    @Transactional(readOnly = true)
    @Override
    public CreatorInfoResponse getCreatorInfo(final long creatorId) {
        Member creator = memberRepository.getById(creatorId);
        return CreatorInfoResponse.from(creator);
    }


}
