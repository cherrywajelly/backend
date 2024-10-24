package com.timeToast.timeToast.service.oAuth;

import com.timeToast.timeToast.dto.member.member.LoginResponse;

public interface OAuthService {
    String loadToLogin();
//    String loadToKakaoLogin();
    LoginResponse getKakaoAccessToken(String accessToken);

    LoginResponse getGoogleAccessToken(String accessToken);

}
