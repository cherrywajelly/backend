package com.timeToast.timeToast.service.oAuth;
import com.timeToast.timeToast.dto.member.LoginResponse;

import static com.timeToast.timeToast.util.TestConstant.*;

public class OAuthServiceTest implements OAuthService {

    @Override
    public String getAccessToken(String accessToken) {
        return TEST_EMAIL.value();
    }
}
