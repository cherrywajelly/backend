package com.timeToast.timeToast.service.member_jwt_refresh;

import com.timeToast.timeToast.domain.member.member_jwt_refresh.MemberJwtRefreshToken;
import com.timeToast.timeToast.repository.member.member_jwt_refresh.MemberJwtRefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberJwtRefreshTokenServiceImpl implements MemberJwtRefreshTokenService {

    private final MemberJwtRefreshTokenRepository memberJwtRefreshTokenRepository;

    public MemberJwtRefreshTokenServiceImpl(final MemberJwtRefreshTokenRepository memberJwtRefreshTokenJpaRepository) {
        this.memberJwtRefreshTokenRepository = memberJwtRefreshTokenJpaRepository;
    }

    @Override
    public MemberJwtRefreshToken save(long memberId, String refreshToken) {
        Optional<MemberJwtRefreshToken> findMemberJwtRefreshToken = memberJwtRefreshTokenRepository.findByMemberId(memberId);
        MemberJwtRefreshToken memberJwtRefreshToken;

        if (findMemberJwtRefreshToken.isPresent()){
            memberJwtRefreshToken = findMemberJwtRefreshToken.get();
            memberJwtRefreshToken.updateRefreshToken(refreshToken);
        }else{
            memberJwtRefreshToken = MemberJwtRefreshToken
                    .builder()
                    .memberId(memberId)
                    .jwt_refresh_token(refreshToken)
                    .build();

            memberJwtRefreshTokenRepository.save(memberJwtRefreshToken);
        }
        return memberJwtRefreshToken;
    }
}
