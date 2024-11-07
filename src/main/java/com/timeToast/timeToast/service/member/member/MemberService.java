package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.dto.member.member.response.MemberInfoResponse;
import com.timeToast.timeToast.dto.member.member.response.MemberProfileResponse;

public interface MemberService {
    void postNickname(final String nickname, final long memberId);
    void nicknameValidation(final String nickname);
    MemberInfoResponse getMemberInfo(final long memberId);
    MemberProfileResponse getMemberProfile(final long memberId);
}
