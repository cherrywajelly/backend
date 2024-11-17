package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.domain.creator_account.CreatorAccount;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.creator.response.CreatorDetailResponse;
import com.timeToast.timeToast.dto.creator.response.CreatorIconInfo;
import com.timeToast.timeToast.dto.creator.response.CreatorResponse;
import com.timeToast.timeToast.dto.creator.response.CreatorResponses;
import com.timeToast.timeToast.dto.member.member.response.MemberInfoResponse;
import com.timeToast.timeToast.dto.member.member.response.MemberProfileResponse;
import com.timeToast.timeToast.dto.premium.response.PremiumResponse;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.exception.ConflictException;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.creator_account.CreatorAccountRepository;
import com.timeToast.timeToast.repository.follow.FollowRepository;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.orders.OrdersRepository;
import com.timeToast.timeToast.repository.premium.PremiumRepository;
import com.timeToast.timeToast.repository.team.team_member.TeamMemberRepository;
import static com.timeToast.timeToast.global.constant.ExceptionConstant.NICKNAME_CONFLICT;
import static com.timeToast.timeToast.global.constant.FileConstant.*;
import static com.timeToast.timeToast.global.constant.SuccessConstant.VALID_NICKNAME;

import com.timeToast.timeToast.service.image.FileUploadService;
import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final FileUploadService fileUploadService;
    private final IconGroupRepository iconGroupRepository;
    private final IconRepository iconRepository;
    private final PremiumRepository premiumRepository;
    private final CreatorAccountRepository creatorAccountRepository;
    private final OrdersRepository orderRepository;

    public MemberServiceImpl(final MemberRepository memberRepository, final FollowRepository followRepository,
                             final TeamMemberRepository teamMemberRepository, final FileUploadService fileUploadService,
                             final IconRepository iconRepository, final PremiumRepository premiumRepository,
                             final IconGroupRepository iconGroupRepository, final CreatorAccountRepository creatorAccountRepository,
                             final OrdersRepository orderRepository) {

        this.memberRepository = memberRepository;
        this.followRepository = followRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.fileUploadService = fileUploadService;
        this.iconRepository = iconRepository;
        this.premiumRepository = premiumRepository;
        this.iconGroupRepository = iconGroupRepository;
        this.creatorAccountRepository = creatorAccountRepository;
        this.orderRepository = orderRepository;
    }

    @Value("${spring.cloud.oci.base-url}")
    private String baseUrl;

    @Transactional
    @Override
    public MemberInfoResponse saveProfileImageByLogin(final long memberId, final MultipartFile profileImage) {
        Member member = memberRepository.getById(memberId);

        String url = baseUrl + MEMBER.value() + SLASH.value() + IMAGE.value() + SLASH.value() + memberId;
        String profileImageUrl = fileUploadService.uploadfile(profileImage,url);

        member.updateProfileUrl(profileImageUrl);
        return MemberInfoResponse.from(member);
    }

    @Transactional
    @Override
    public MemberInfoResponse postNickname(final String nickname, final long memberId){
        if (memberRepository.existsByNickname(nickname)) {
            throw new ConflictException(NICKNAME_CONFLICT.getMessage());
        }
        Member member = memberRepository.getById(memberId);
        member.updateNickname(nickname);
        return MemberInfoResponse.from(member);
    }

    @Transactional(readOnly = true)
    @Override
    public Response nicknameValidation(final String nickname) {

        if(memberRepository.existsByNickname(nickname)){
            throw new ConflictException(NICKNAME_CONFLICT.getMessage());
        }
        return new Response(StatusCode.OK.getStatusCode(), VALID_NICKNAME.getMessage());
    }

    @Transactional(readOnly = true)
    @Override
    public MemberInfoResponse getMemberInfo(final long memberId) {
        Member member = memberRepository.getById(memberId);
        return MemberInfoResponse.from(member);
    }

    @Transactional(readOnly = true)
    @Override
    public MemberProfileResponse getMemberProfileByLogin(final long memberId) {
        return getMemberProfile(memberId, memberId);
    }

    @Transactional(readOnly = true)
    @Override
    public MemberProfileResponse getMemberProfile(final long loginId, final long memberId) {
        Member member = memberRepository.getById(memberId);
        return MemberProfileResponse.builder()
                .nickname(member.getNickname())
                .profileUrl(member.getMemberProfileUrl())
                .followingCount(followRepository.findAllByFollowerId(memberId).size())
                .followerCount(followRepository.findAllByFollowingId(memberId).size())
                .teamCount(teamMemberRepository.findAllByMemberId(memberId).size())
                .isFollow(followRepository.findByFollowingIdAndFollowerId(memberId, loginId).isPresent())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public CreatorResponses getCreators() {
        List<CreatorResponse> creatorResponses = memberRepository.findAllByMemberRole(MemberRole.CREATOR).stream()
                .sorted(Comparator.comparing(Member::getNickname)).map(CreatorResponse::from).toList();
        return new CreatorResponses(creatorResponses);
    }

    @Transactional(readOnly = true)
    @Override
    public CreatorDetailResponse getCreatorByCreatorId(final long creatorId) {
        List<IconGroup> iconGroups = iconGroupRepository.findAllByMemberId(creatorId);
        List<CreatorIconInfo> creatorIconInfos = new ArrayList<>();
        iconGroups.forEach(
        iconGroup ->
                {
                    int salesIconCount = orderRepository.findAllByIconGroupId(iconGroup.getId()).size();
                    creatorIconInfos.add(
                            CreatorIconInfo.builder()
                                    .title(iconGroup.getName())
                                    //TODO
                                    .revenue(salesIconCount*iconGroup.getPrice())
                                    .salesIconCount(salesIconCount)
                                    .iconImageUrl(iconRepository.findAllByIconGroupId(iconGroup.getId()).stream().map(Icon::getIconImageUrl).toList())
                                    .build()
                    );
                }
        );

        Member member = memberRepository.getById(creatorId);
        String creatorAccount = null;
        Optional<CreatorAccount> findCreatorAccount = creatorAccountRepository.findByMemberId(creatorId);
        if(findCreatorAccount.isPresent()){
            creatorAccount = findCreatorAccount.get().getAccountNumber();
        }

        return CreatorDetailResponse.builder()
                .profileUrl(member.getMemberProfileUrl())
                .nickname(member.getNickname())
                .iconTotalCount(iconGroups.size())
                .salesIconTotalCount(creatorIconInfos.stream().mapToInt(CreatorIconInfo::salesIconCount).sum())
                .totalRevenue(creatorIconInfos.stream().mapToInt(CreatorIconInfo::revenue).sum())
                .accountNumber(creatorAccount)
                .creatorIconInfos(creatorIconInfos)
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public PremiumResponse getMemberPremium(final long memberId) {
        Member member = memberRepository.getById(memberId);
        return PremiumResponse.from(premiumRepository.getById(member.getPremiumId()));
    }

}
