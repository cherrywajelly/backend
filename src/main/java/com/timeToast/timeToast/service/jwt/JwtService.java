package com.timeToast.timeToast.service.jwt;

import com.timeToast.timeToast.domain.member.LoginMember;
import com.timeToast.timeToast.dto.member.LoginResponse;

public interface JwtService {

    LoginResponse createJwts(final LoginMember member);

    String createToken(final LoginMember loginMember, final long expired);
}