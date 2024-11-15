package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.domain.enums.premium.PremiumType;
import com.timeToast.timeToast.dto.creator.response.CreatorDetailResponse;
import com.timeToast.timeToast.dto.creator.response.CreatorResponses;
import com.timeToast.timeToast.dto.member.member.response.MemberInfoResponse;
import com.timeToast.timeToast.dto.member.member.response.MemberProfileResponse;
import com.timeToast.timeToast.dto.premium.response.PremiumResponse;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;

public class MemberServiceTest implements MemberService{

    @Override
    public MemberInfoResponse saveProfileImageByLogin(long memberId, MultipartFile profileImage) {
        return new MemberInfoResponse(1L, "nickname","profileUrl");
    }

    @Override
    public void postNickname(String nickname, long memberId) {

    }

    @Override
    public void nicknameValidation(String nickname) {

    }

    @Override
    public MemberInfoResponse getMemberInfo(long memberId) {

        return new MemberInfoResponse(1L,"nickname","profileUrl");
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
        return null;
    }

    @Override
    public CreatorResponses getCreators() {
        return null;
    }

    @Override
    public PremiumResponse getMemberPremium(long memberId) {
        return new PremiumResponse(1L, PremiumType.BASIC, 0, 3, "description");
    }


}