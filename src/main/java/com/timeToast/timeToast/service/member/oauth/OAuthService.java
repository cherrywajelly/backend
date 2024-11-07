package com.timeToast.timeToast.service.member.oauth;

import com.timeToast.timeToast.dto.member.member.response.LoginResponse;

public interface OAuthService {
//    String loadToLogin();
//    String loadToKakaoLogin();
    LoginResponse getKakaoAccessToken(String accessToken);

    LoginResponse getGoogleAccessToken(String accessToken);

}
