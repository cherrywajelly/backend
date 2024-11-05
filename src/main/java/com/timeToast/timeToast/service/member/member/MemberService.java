package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.dto.member.member.MemberResponse;

public interface MemberService {

    void postNickname(final String nickname, final long memberId);
    void isNicknameAvailable(final String nickname);
    MemberResponse getNickname(final long memberId);
}
