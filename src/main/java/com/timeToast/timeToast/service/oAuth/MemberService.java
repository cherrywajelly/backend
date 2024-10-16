package com.timeToast.timeToast.service.oAuth;

import org.springframework.http.ResponseEntity;

public interface MemberService {

    void postNickname(String nickname, long userId);

    void isNicknameAvailable(String nickname);
}
