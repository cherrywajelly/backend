package com.timeToast.timeToast.service.jwt;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.member.member.LoginResponse;

public interface JwtService {

    LoginResponse createJwts(final LoginMember member, final boolean isNew);
    String createToken(final LoginMember loginMember, final long expired);
    LoginResponse tokenRenewal(final String refreshToken);
}
