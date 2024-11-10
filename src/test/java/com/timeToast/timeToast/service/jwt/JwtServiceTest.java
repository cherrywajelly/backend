package com.timeToast.timeToast.service.jwt;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.member.member.response.LoginResponse;

import static com.timeToast.timeToast.util.TestConstant.TEST_ACCESS_TOKEN;
import static com.timeToast.timeToast.util.TestConstant.TEST_REFRESH_TOKEN;
import static org.junit.jupiter.api.Assertions.*;

public class JwtServiceTest implements JwtService{

    @Override
    public LoginResponse createJwts(LoginMember member, boolean isNew) {
        return new LoginResponse(TEST_ACCESS_TOKEN.value(), TEST_REFRESH_TOKEN.value(), false);
    }

    @Override
    public String createToken(LoginMember loginMember, long expired) {
        return TEST_ACCESS_TOKEN.value();
    }

    @Override
    public LoginResponse tokenRenewal(String refreshToken) {
        return new LoginResponse(TEST_ACCESS_TOKEN.value(), TEST_REFRESH_TOKEN.value(), false);
    }
}