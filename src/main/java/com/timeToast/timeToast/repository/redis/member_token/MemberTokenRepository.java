package com.timeToast.timeToast.repository.redis.member_token;

import com.timeToast.timeToast.domain.member.member_token.MemberToken;

import java.util.Optional;

public interface MemberTokenRepository {
    MemberToken save(final MemberToken memberJwtRefreshToken);
    Optional<MemberToken> findById(final long memberId);
    void deleteById(final long memberId);
}
