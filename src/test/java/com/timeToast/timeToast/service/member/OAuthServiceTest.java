package com.timeToast.timeToast.service.member;
import com.timeToast.timeToast.dto.member.member.response.LoginResponse;
import com.timeToast.timeToast.service.member.oauth.OAuthService;

import static com.timeToast.timeToast.util.TestConstant.*;

public class OAuthServiceTest implements OAuthService {

    @Override
    public LoginResponse getKakaoAccessToken(String accessToken) {
        return new LoginResponse(TEST_ACCESS_TOKEN.value(), TEST_REFRESH_TOKEN.value(), true);
    }

    @Override
    public LoginResponse getGoogleAccessToken(String accessToken) {
        return new LoginResponse(TEST_ACCESS_TOKEN.value(), TEST_REFRESH_TOKEN.value(), true);
    }
}
