package com.timeToast.timeToast.repository.member_jwt_refresh;

import com.timeToast.timeToast.domain.member_jwt_refresh.MemberJwtRefreshToken;

import java.util.Optional;

public interface MemberJwtRefreshTokenRepository {

    MemberJwtRefreshToken save(final MemberJwtRefreshToken memberJwtRefreshToken);
    Optional<MemberJwtRefreshToken> findByMemberId(final long memberId);
}
