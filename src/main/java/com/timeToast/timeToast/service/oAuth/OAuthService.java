package com.timeToast.timeToast.service.oAuth;

import com.timeToast.timeToast.dto.member.LoginResponse;

public interface OAuthService {
    LoginResponse getKakaoAccessToken(String accessToken);

    LoginResponse getGoogleAccessToken(String accessToken);

}
