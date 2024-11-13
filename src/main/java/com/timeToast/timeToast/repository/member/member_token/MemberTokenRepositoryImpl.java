package com.timeToast.timeToast.repository.member.member_token;

import com.timeToast.timeToast.domain.member.member_token.MemberToken;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MemberTokenRepositoryImpl implements MemberTokenRepository {

    private final MemberTokenJpaRepository memberTokenJpaRepository;

    public MemberTokenRepositoryImpl(MemberTokenJpaRepository memberJwtRefreshTokenJpaRepository) {
        this.memberTokenJpaRepository = memberJwtRefreshTokenJpaRepository;
    }

    @Override
    public MemberToken save(final MemberToken memberJwtRefreshToken) {
        return memberTokenJpaRepository.save(memberJwtRefreshToken);
    }

    @Override
    public Optional<MemberToken> findByMemberId(final long memberId) {
        return memberTokenJpaRepository.findByMemberId(memberId);
    }

    @Override
    public void deleteByMemberId(final long memberId) {
        memberTokenJpaRepository.deleteByMemberId(memberId);
    }

}
