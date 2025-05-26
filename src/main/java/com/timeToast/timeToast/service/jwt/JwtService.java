package com.timeToast.timeToast.service.jwt;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.member.Login;

public interface JwtService {

    Login createJwts(final LoginMember member, final boolean isNew);
    Login tokenRenewal(final String refreshToken);
}
