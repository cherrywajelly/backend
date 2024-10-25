package com.timeToast.timeToast.service.member.member;

public interface MemberService {

    void postNickname(String nickname, long userId);

    void isNicknameAvailable(String nickname);
}
