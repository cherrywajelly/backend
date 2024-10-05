package com.timeToast.timeToast.service.oauth;

import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.dto.member.LoginResponse;
import org.springframework.http.ResponseEntity;

public interface LoginService {

    //for login test
     String loadToKakaoLogin();

     String loadToGoogleLogin();

    LoginResponse getAccessToken(LoginType social, String code);

    ResponseEntity<String> postNickname(String nickname);

}
