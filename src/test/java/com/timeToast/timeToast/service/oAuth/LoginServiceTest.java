package com.timeToast.timeToast.service.oAuth;

import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.dto.member.LoginResponse;
import org.springframework.stereotype.Service;

import static com.timeToast.timeToast.util.TestConstant.*;

public class LoginServiceTest implements LoginService{

    @Override
    public LoginResponse loginToService(String email, LoginType loginType) {
        return new LoginResponse(TEST_ACCESS_TOKEN.value(), TEST_REFRESH_TOKEN.value());
    }
}
