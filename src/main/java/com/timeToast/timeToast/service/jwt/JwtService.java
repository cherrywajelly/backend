package com.timeToast.timeToast.service.jwt;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.member.member.response.LoginResponse;

public interface JwtService {

    LoginResponse createJwts(final LoginMember member, final boolean isNew);
    LoginResponse tokenRenewal(final String refreshToken);
}
