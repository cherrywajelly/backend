package com.timeToast.timeToast.service.jwt;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.member.member.response.LoginResponse;

import static org.junit.jupiter.api.Assertions.*;

public class JwtServiceTest implements JwtService{

    @Override
    public LoginResponse createJwts(LoginMember member, boolean isNew) {
        return null;
    }

    @Override
    public String createToken(LoginMember loginMember, long expired) {
        return null;
    }

    @Override
    public LoginResponse tokenRenewal(String refreshToken) {
        return null;
    }
}