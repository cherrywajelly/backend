package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.domain.enums.creator_account.Bank;
import com.timeToast.timeToast.domain.enums.premium.PremiumType;
import com.timeToast.timeToast.dto.creator.response.CreatorDetailResponse;
import com.timeToast.timeToast.dto.creator.response.CreatorResponse;
import com.timeToast.timeToast.dto.creator.response.CreatorResponses;
import com.timeToast.timeToast.dto.member.member.request.CreatorRequest;
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
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;

public class MemberServiceTest implements MemberService{

    @Override
    public MemberInfoResponse saveProfileImageByLogin(long memberId, MultipartFile profileImage) {
        return new MemberInfoResponse(1L, "nickname","profileUrl","email");
    }

    @Override
    public MemberInfoResponse postNickname(String nickname, long memberId) {
        if(nickname.equals("conflictNickname")){
            throw new ConflictException(NICKNAME_CONFLICT.getMessage());
        }
        return new MemberInfoResponse(1L, "nickname","profileUrl","email");
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

        return new MemberInfoResponse(1L,"nickname","profileUrl","email");
    }

    @Override
    public MemberProfileResponse getMemberProfileByLogin(long memberId) {
        return new MemberProfileResponse("nickname", "profileUrl", 0,0,1, false);
    }

    @Override
    public MemberProfileResponse getMemberProfile(long loginId, long memberId) {
        return new MemberProfileResponse("nickname", "profileUrl", 0,0,1, false);
    }

    @Override
    public CreatorDetailResponse getCreatorByCreatorId(final long creatorId){
        return new CreatorDetailResponse("profileUrl", "nickname", Bank.IBK.value(),"accountNumber");
    }

    @Override
    public CreatorResponses getCreators() {
        List<CreatorResponse> creatorResponses = new ArrayList<>();
        creatorResponses.add(
                CreatorResponse.builder()
                        .memberId(1L)
                        .profileUrl("profileUrl")
                        .nickname("nickname")
                        .salesIconCount(10)
                        .totalRevenue(100)
                        .createdIconCount(10)
                        .build()
        );
        return new CreatorResponses(creatorResponses) ;
    }

    @Override
    public MemberPremium getMemberPremium(final long memberId) {
        return new MemberPremium(1L, PremiumType.BASIC, LocalDate.now());
    }

    @Override
    public Response saveCreatorInfo(final long creatorId, final MultipartFile profile, final CreatorRequest creatorRequest) {
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_POST.getMessage());
    }
}