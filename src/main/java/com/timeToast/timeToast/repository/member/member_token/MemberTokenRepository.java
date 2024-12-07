package com.timeToast.timeToast.repository.member.member_token;

import com.timeToast.timeToast.domain.member.member_token.MemberToken;

import java.util.Optional;

public interface MemberTokenRepository {
    MemberToken save(final MemberToken memberJwtRefreshToken);
    Optional<MemberToken> findByMemberId(final long memberId);
    Optional<MemberToken> findByFcmToken(final String token);
    void deleteByMemberId(final long memberId);
}
