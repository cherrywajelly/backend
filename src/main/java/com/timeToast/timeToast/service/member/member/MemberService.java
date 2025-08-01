package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.member.member.request.CreatorAccount;
import com.timeToast.timeToast.dto.member.member.response.CreatorInfoResponse;
import com.timeToast.timeToast.dto.member.member.response.CreatorResponses;
import com.timeToast.timeToast.dto.member.member.response.*;
import com.timeToast.timeToast.dto.premium.response.MemberPremium;
import com.timeToast.timeToast.global.response.Response;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {
    MemberInfoResponse saveToStaff(final long memberId);
    MemberInfoResponse saveToCreators(final long memberId);
    MemberInfoResponse saveToUser(final long memberId);
    MemberInfoResponse saveProfileImage(final long memberId, final MultipartFile profileImage);
    Response nicknameValidation(final String nickname);
    MemberInfoResponse saveNickname(final String nickname, final long memberId);
    CreatorInfoResponse saveCreatorInfo(final long creatorId, final CreatorAccount creatorAccount);
    MemberInfoResponse getMemberInfo(final long memberId);
    MemberPremium getMemberPremiumByMember(final Member member);
    MemberProfileResponse getMemberProfile(final long memberId);
    MemberProfileResponse getMemberProfile(final long loginId, final long memberId);
    CreatorResponses getCreators();
    CreatorInfoResponse getCreatorInfo(final long creatorId);
}
