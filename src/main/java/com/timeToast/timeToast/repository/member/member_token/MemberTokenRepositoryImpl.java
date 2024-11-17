package com.timeToast.timeToast.repository.member.member_token;

import com.timeToast.timeToast.domain.member.member_token.MemberToken;
import com.timeToast.timeToast.global.exception.NotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.INVALID_FCM_TOKEN;

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
    public Optional<MemberToken> findByFcmToken(final String token) {
        return memberTokenJpaRepository.findByFcmToken(token);

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
