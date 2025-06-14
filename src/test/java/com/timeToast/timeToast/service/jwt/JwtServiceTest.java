package com.timeToast.timeToast.service.jwt;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.member.LoginResponse;

import static com.timeToast.timeToast.util.TestConstant.TEST_ACCESS_TOKEN;
import static com.timeToast.timeToast.util.TestConstant.TEST_REFRESH_TOKEN;

public class JwtServiceTest implements JwtService{

    @Override
    public LoginResponse createJwts(LoginMember member, boolean isNew) {
        return new LoginResponse(TEST_ACCESS_TOKEN.value(), TEST_REFRESH_TOKEN.value(), false);
    }

    @Override
    public LoginResponse tokenRenewal(String refreshToken) {
        return new LoginResponse(TEST_ACCESS_TOKEN.value(), TEST_REFRESH_TOKEN.value(), false);
    }
}