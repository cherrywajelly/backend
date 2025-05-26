package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.dto.member.member.response.CreatorInfoResponse;
import com.timeToast.timeToast.dto.member.member.response.CreatorResponses;
import com.timeToast.timeToast.dto.member.member.request.CreatorRequest;
import com.timeToast.timeToast.dto.member.member.response.*;
import com.timeToast.timeToast.dto.premium.response.MemberPremium;
import com.timeToast.timeToast.global.response.Response;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {

    MemberInfoResponse saveProfileImage(final long memberId, final MultipartFile profileImage);
    MemberInfoResponse saveNickname(final String nickname, final long memberId);
    Response nicknameValidation(final String nickname);
    MemberInfoResponse getMemberInfo(final long memberId);
    MemberProfileResponse getMemberProfileByLogin(final long memberId);
    MemberProfileResponse getMemberProfile(final long loginId, final long memberId);
    CreatorResponses getCreators();
    CreatorInfoResponse getCreatorMemberInfo(final long creatorId);
    MemberPremium getMemberPremium(final long memberId);
    CreatorInfoResponse saveCreatorInfo(final long creatorId, final MultipartFile profile, final CreatorRequest creatorRequest);
    CreatorProfileResponse getCreatorProfile(final long memberId);
//    void getCreatorMonthSettlement(final int year, final int month);
//    getCreatorMonthSettlementByCreatorId();
}
