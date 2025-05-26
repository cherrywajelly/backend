package com.timeToast.timeToast.service.member.oauth;
import com.timeToast.timeToast.dto.member.Login;

import static com.timeToast.timeToast.util.TestConstant.*;

public class OAuthServiceTest implements OAuthService {

    @Override
    public Login kakaoLoginMember(String accessToken) {
        return new Login(TEST_ACCESS_TOKEN.value(), TEST_REFRESH_TOKEN.value(), true);
    }

    @Override
    public Login kakaoLoginCreator(String accessToken) {
        return new Login(TEST_ACCESS_TOKEN.value(), TEST_REFRESH_TOKEN.value(), true);
    }

    @Override
    public Login kakaoLoginAdmin(String accessToken) {
        return new Login(TEST_ACCESS_TOKEN.value(), TEST_REFRESH_TOKEN.value(), true);
    }

    @Override
    public Login googleLoginMember(String accessToken) {
        return new Login(TEST_ACCESS_TOKEN.value(), TEST_REFRESH_TOKEN.value(), true);
    }

    @Override
    public Login googleLoginCreator(String accessToken) {
        return new Login(TEST_ACCESS_TOKEN.value(), TEST_REFRESH_TOKEN.value(), true);
    }

    @Override
    public Login googleLoginAdmin(String accessToken) {
        return new Login(TEST_ACCESS_TOKEN.value(), TEST_REFRESH_TOKEN.value(), true);
    }
}
