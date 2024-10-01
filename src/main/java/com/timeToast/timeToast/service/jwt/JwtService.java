package com.timeToast.timeToast.service.jwt;

import com.timeToast.timeToast.dto.member.LoginResponse;

public interface JwtService {

    LoginResponse createJwts(final long memberId);

    String createToken(final long memberId, final long expired);

}
