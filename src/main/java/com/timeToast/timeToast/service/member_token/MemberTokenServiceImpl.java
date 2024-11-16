package com.timeToast.timeToast.service.member_token;

import com.timeToast.timeToast.domain.member.member_token.MemberToken;
import com.timeToast.timeToast.repository.member.member_token.MemberTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MemberTokenServiceImpl implements MemberTokenService {

    private final MemberTokenRepository memberTokenRepository;

    public MemberTokenServiceImpl(final MemberTokenRepository memberTokenRepository) {
        this.memberTokenRepository = memberTokenRepository;
    }

    @Transactional
    @Override
    public MemberToken save(long memberId, String refreshToken) {
        Optional<MemberToken> findMemberJwtRefreshToken = memberTokenRepository.findByMemberId(memberId);
        MemberToken memberJwtRefreshToken;

        if (findMemberJwtRefreshToken.isPresent()){
            memberJwtRefreshToken = findMemberJwtRefreshToken.get();
            memberJwtRefreshToken.updateRefreshToken(refreshToken);
        }else{
            memberJwtRefreshToken = MemberToken
                    .builder()
                    .memberId(memberId)
                    .jwt_refresh_token(refreshToken)
                    .build();

            memberTokenRepository.save(memberJwtRefreshToken);
        }
        return memberJwtRefreshToken;
    }
}
