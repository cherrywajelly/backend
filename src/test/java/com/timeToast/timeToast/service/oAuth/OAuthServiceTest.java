package com.timeToast.timeToast.service.oAuth;
import com.timeToast.timeToast.dto.member.member.LoginResponse;

import static com.timeToast.timeToast.util.TestConstant.*;

public class OAuthServiceTest implements OAuthService {

    @Override
    public LoginResponse getKakaoAccessToken(String accessToken) {
        return new LoginResponse(TEST_ACCESS_TOKEN.value(), TEST_REFRESH_TOKEN.value());
    }

    @Override
    public LoginResponse getGoogleAccessToken(String accessToken) {
        return new LoginResponse(TEST_ACCESS_TOKEN.value(), TEST_REFRESH_TOKEN.value());
    }
}
