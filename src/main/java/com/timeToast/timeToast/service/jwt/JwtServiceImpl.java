package com.timeToast.timeToast.service.jwt;

import com.timeToast.timeToast.dto.member.LoginResponse;
import com.timeToast.timeToast.service.member_jwt_refresh.MemberJwtRefreshTokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import static com.timeToast.timeToast.global.constant.JwtKey.JWT_KEY;
import static com.timeToast.timeToast.global.constant.TimeConstant.ONE_HOUR;
import static com.timeToast.timeToast.global.constant.TimeConstant.ONE_DAY;


@Service
public class JwtServiceImpl implements JwtService {

    private final MemberJwtRefreshTokenService memberJwtRefreshTokenService;

    public JwtServiceImpl(final MemberJwtRefreshTokenService memberJwtRefreshTokenService) {
        this.memberJwtRefreshTokenService = memberJwtRefreshTokenService;
    }

    @Override
    public LoginResponse createJwts(long memberId) {
        String accessToken = createToken(memberId, ONE_HOUR.time());
        String refreshToken = createToken(memberId, ONE_DAY.time());
        memberJwtRefreshTokenService.save(memberId, refreshToken);

        return LoginResponse.of(accessToken, refreshToken);
    }

    @Override
    public String createToken(final long memberId, final long expired) {

        Date now = new Date();
        Date expiredDate = new Date( now.getTime() + expired);

        SecretKey tokenKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(JWT_KEY));

        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setIssuer("timeToast.com")
                .setSubject(String.valueOf(memberId))
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(tokenKey)
                .compact();
    }
}
