package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.dto.creator.response.CreatorDetailResponse;
import com.timeToast.timeToast.dto.creator.response.CreatorResponses;
import com.timeToast.timeToast.dto.member.member.request.CreatorRequest;
import com.timeToast.timeToast.dto.member.member.response.MemberInfoResponse;
import com.timeToast.timeToast.dto.member.member.response.MemberManagerResponses;
import com.timeToast.timeToast.dto.member.member.response.MemberProfileResponse;
import com.timeToast.timeToast.dto.premium.response.MemberPremium;
import com.timeToast.timeToast.dto.premium.response.PremiumResponse;
import com.timeToast.timeToast.global.response.Response;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {

    MemberInfoResponse saveProfileImageByLogin(final long memberId, final MultipartFile profileImage);
    MemberInfoResponse postNickname(final String nickname, final long memberId);
    Response nicknameValidation(final String nickname);
    MemberInfoResponse getMemberInfo(final long memberId);
    MemberProfileResponse getMemberProfileByLogin(final long memberId);
    MemberProfileResponse getMemberProfile(final long loginId, final long memberId);
    CreatorResponses getCreators();
    CreatorDetailResponse getCreatorByCreatorId(final long creatorId);
    MemberPremium getMemberPremium(final long memberId);
    Response saveCreatorInfo(final long creatorId, final MultipartFile profile, final CreatorRequest creatorRequest);
    MemberManagerResponses getMembersForManagers();
//    void getCreatorMonthSettlement(final int year, final int month);
//    getCreatorMonthSettlementByCreatorId();
}
