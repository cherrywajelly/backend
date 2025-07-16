package com.timeToast.timeToast.repository.redis.member_token;

import com.timeToast.timeToast.domain.member.member_token.MemberToken;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class MemberTokenRepositoryImpl implements MemberTokenRepository {

    private final MemberTokenRedisRepository memberTokenRedisRepository;

    public MemberTokenRepositoryImpl(MemberTokenRedisRepository memberJwtRefreshTokenJpaRepository) {
        this.memberTokenRedisRepository = memberJwtRefreshTokenJpaRepository;
    }

    @Override
    public MemberToken save(final MemberToken memberJwtRefreshToken) {
        return memberTokenRedisRepository.save(memberJwtRefreshToken);
    }


    @Override
    public Optional<MemberToken> findById(final long memberId) {
        return memberTokenRedisRepository.findByMemberId(memberId);
    }

    @Override
    public void deleteById(final long memberId) {
        memberTokenRedisRepository.deleteByMemberId(memberId);
    }

}
