package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.dto.creator.response.CreatorDetailResponse;
import com.timeToast.timeToast.dto.creator.response.CreatorResponses;
import com.timeToast.timeToast.dto.member.member.response.MemberInfoResponse;
import com.timeToast.timeToast.dto.member.member.response.MemberProfileResponse;
import com.timeToast.timeToast.dto.premium.response.PremiumResponse;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {

    MemberInfoResponse saveProfileImageByLogin(final long memberId, final MultipartFile profileImage);
    void postNickname(final String nickname, final long memberId);
    void nicknameValidation(final String nickname);
    MemberInfoResponse getMemberInfo(final long memberId);
    MemberProfileResponse getMemberProfileByLogin(final long memberId);
    MemberProfileResponse getMemberProfile(final long loginId, final long memberId);
    CreatorResponses getCreators();
    CreatorDetailResponse getCreatorByCreatorId(final long creatorId);
    PremiumResponse getMemberPremium(final long memberId);
    void getCreatorMonthSettlement(final int year, final int month);
    getCreatorMonthSettlementByCreatorId();
}
