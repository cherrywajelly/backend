package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.domain.enums.member.Bank;
import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.enums.premium.PremiumType;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.member.member.request.CreatorAccount;
import com.timeToast.timeToast.dto.member.member.response.CreatorInfoResponse;
import com.timeToast.timeToast.dto.member.member.response.CreatorResponse;
import com.timeToast.timeToast.dto.member.member.response.CreatorResponses;
import com.timeToast.timeToast.dto.member.member.response.*;
import com.timeToast.timeToast.dto.premium.response.MemberPremium;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.constant.SuccessConstant;
import com.timeToast.timeToast.global.exception.ConflictException;
import com.timeToast.timeToast.global.response.Response;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.NICKNAME_CONFLICT;

public class MemberServiceTest implements MemberService{

    private MemberPremium setMockMemberPremium(){
        return new MemberPremium(1L, PremiumType.BASIC, LocalDate.now());
    }

    private MemberPremium getMemberPremium() {
        return new MemberPremium(1L, PremiumType.BASIC, LocalDate.now());
    }
    @Override
    public MemberInfoResponse saveToStaff(long memberId) {
        return new MemberInfoResponse(1L, "nickname", "memberProfileUrl", "email",MemberRole.STAFF, LoginType.GOOGLE, getMemberPremium());
    }

    @Override
    public MemberInfoResponse saveToCreators(long memberId) {
        return new MemberInfoResponse(1L, "nickname", "memberProfileUrl", "email",MemberRole.CREATOR,LoginType.GOOGLE, getMemberPremium());
    }

    @Override
    public MemberInfoResponse saveToUser(long memberId) {
        return new MemberInfoResponse(1L, "nickname", "memberProfileUrl", "email",MemberRole.USER,LoginType.GOOGLE, getMemberPremium());
    }

    @Override
    public MemberInfoResponse saveProfileImage(long memberId, MultipartFile profileImage) {
        return new MemberInfoResponse(1L, "nickname","profileUrl",
                "email",  MemberRole.USER,LoginType.GOOGLE, setMockMemberPremium());
    }

    @Override
    public MemberInfoResponse saveNickname(String nickname, long memberId) {
        if(nickname.equals("conflictNickname")){
            throw new ConflictException(NICKNAME_CONFLICT.getMessage());
        }
        return new MemberInfoResponse(1L, "nickname","profileUrl",
                "email", MemberRole.USER,LoginType.GOOGLE,  setMockMemberPremium());
    }

    @Override
    public Response nicknameValidation(String nickname) {
        if(nickname.equals("conflictNickname")){
            throw new ConflictException(NICKNAME_CONFLICT.getMessage());
        }
        return new Response(StatusCode.OK.getStatusCode(), SuccessConstant.VALID_NICKNAME.getMessage());
    }

    @Override
    public MemberInfoResponse getMemberInfo(long memberId) {
        return new MemberInfoResponse(1L,"nickname","profileUrl",
                "email", MemberRole.USER,LoginType.GOOGLE, setMockMemberPremium());
    }

    @Override
    public MemberPremium getMemberPremiumByMember(Member member) {
        return setMockMemberPremium();
    }

    @Override
    public MemberProfileResponse getMemberProfile(long memberId) {
        return new MemberProfileResponse("nickname", "profileUrl",  false);
    }

    @Override
    public MemberProfileResponse getMemberProfile(long loginId, long memberId) {
        return new MemberProfileResponse("nickname", "profileUrl", false);
    }

    @Override
    public CreatorInfoResponse getCreatorInfo(final long creatorId){
        return new CreatorInfoResponse("nickname",Bank.IBK,"accountNumber","profileUrl");
    }

    @Override
    public CreatorResponses getCreators() {
        List<CreatorResponse> creatorResponses = new ArrayList<>();
        creatorResponses.add(
                CreatorResponse.builder()
                        .creatorInfo(new CreatorInfoResponse("nickname",Bank.IBK,"accountNumber","profileUrl"))
                        .salesIconCount(10)
                        .totalRevenue(100)
                        .createdIconCount(10)
                        .build()
        );
        return new CreatorResponses(creatorResponses) ;
    }

    @Override
    public CreatorInfoResponse saveCreatorInfo(final long creatorId, final CreatorAccount creatorAccount) {
        return CreatorInfoResponse.builder()
                .nickname("nickname")
                .profileUrl("profileUrl")
                .bank(creatorAccount.bank())
                .accountNumber(creatorAccount.accountNumber())
                .build();
    }


}