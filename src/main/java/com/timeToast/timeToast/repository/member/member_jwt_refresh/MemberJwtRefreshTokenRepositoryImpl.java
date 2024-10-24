package com.timeToast.timeToast.repository.member.member_jwt_refresh;

import com.timeToast.timeToast.domain.member.member_jwt_refresh.MemberJwtRefreshToken;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MemberJwtRefreshTokenRepositoryImpl implements MemberJwtRefreshTokenRepository{

    private final MemberJwtRefreshTokenJpaRepository memberJwtRefreshTokenJpaRepository;

    public MemberJwtRefreshTokenRepositoryImpl(MemberJwtRefreshTokenJpaRepository memberJwtRefreshTokenJpaRepository) {
        this.memberJwtRefreshTokenJpaRepository = memberJwtRefreshTokenJpaRepository;
    }

    @Override
    public MemberJwtRefreshToken save(final MemberJwtRefreshToken memberJwtRefreshToken) {
        return memberJwtRefreshTokenJpaRepository.save(memberJwtRefreshToken);
    }

    @Override
    public Optional<MemberJwtRefreshToken> findByMemberId(final long memberId) {
        return memberJwtRefreshTokenJpaRepository.findByMemberId(memberId);
    }
}
