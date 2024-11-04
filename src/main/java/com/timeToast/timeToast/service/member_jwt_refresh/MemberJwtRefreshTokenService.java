package com.timeToast.timeToast.service.member_jwt_refresh;

import com.timeToast.timeToast.domain.member.member_jwt_refresh.MemberJwtRefreshToken;

public interface MemberJwtRefreshTokenService {

    MemberJwtRefreshToken save(final long memberId, final String refreshToken);
}
