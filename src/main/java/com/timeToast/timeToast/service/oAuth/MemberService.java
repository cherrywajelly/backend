package com.timeToast.timeToast.service.oAuth;

import org.springframework.http.ResponseEntity;

public interface MemberService {

    ResponseEntity<String> postNickname(String nickname, long userId);
}
