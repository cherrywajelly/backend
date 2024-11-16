package com.timeToast.timeToast.service.member.oauth;
import com.timeToast.timeToast.dto.member.member.response.LoginResponse;
import com.timeToast.timeToast.service.member.oauth.OAuthService;

import static com.timeToast.timeToast.util.TestConstant.*;

public class OAuthServiceTest implements OAuthService {

    @Override
    public LoginResponse kakaoLoginMember(String accessToken) {
        return new LoginResponse(TEST_ACCESS_TOKEN.value(), TEST_REFRESH_TOKEN.value(), true);
    }

    @Override
    public LoginResponse kakaoLoginCreator(String accessToken) {
        return new LoginResponse(TEST_ACCESS_TOKEN.value(), TEST_REFRESH_TOKEN.value(), true);
    }

    @Override
    public LoginResponse kakaoLoginAdmin(String accessToken) {
        return new LoginResponse(TEST_ACCESS_TOKEN.value(), TEST_REFRESH_TOKEN.value(), true);
    }

    @Override
    public LoginResponse googleLoginMember(String accessToken) {
        return new LoginResponse(TEST_ACCESS_TOKEN.value(), TEST_REFRESH_TOKEN.value(), true);
    }

    @Override
    public LoginResponse googleLoginCreator(String accessToken) {
        return new LoginResponse(TEST_ACCESS_TOKEN.value(), TEST_REFRESH_TOKEN.value(), true);
    }

    @Override
    public LoginResponse googleLoginAdmin(String accessToken) {
        return new LoginResponse(TEST_ACCESS_TOKEN.value(), TEST_REFRESH_TOKEN.value(), true);
    }
}
