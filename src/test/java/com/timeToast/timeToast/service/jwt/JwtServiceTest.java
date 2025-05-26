package com.timeToast.timeToast.service.jwt;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.member.Login;

import static com.timeToast.timeToast.util.TestConstant.TEST_ACCESS_TOKEN;
import static com.timeToast.timeToast.util.TestConstant.TEST_REFRESH_TOKEN;

public class JwtServiceTest implements JwtService{

    @Override
    public Login createJwts(LoginMember member, boolean isNew) {
        return new Login(TEST_ACCESS_TOKEN.value(), TEST_REFRESH_TOKEN.value(), false);
    }

    @Override
    public Login tokenRenewal(String refreshToken) {
        return new Login(TEST_ACCESS_TOKEN.value(), TEST_REFRESH_TOKEN.value(), false);
    }
}