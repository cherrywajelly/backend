package com.timeToast.timeToast.service.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.timeToast.timeToast.domain.member.LoginMember;
import com.timeToast.timeToast.domain.member_jwt_refresh.MemberJwtRefreshToken;
import com.timeToast.timeToast.dto.member.LoginResponse;
import com.timeToast.timeToast.global.exception.InternalServerException;
import com.timeToast.timeToast.service.member_jwt_refresh.MemberJwtRefreshTokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.LOGIN_INTERCEPTOR_JSON_PROCESSING_ERROR;
import static com.timeToast.timeToast.global.constant.JwtKey.JWT_KEY;
import static com.timeToast.timeToast.global.constant.TimeConstant.ONE_HOUR;
import static com.timeToast.timeToast.global.constant.TimeConstant.ONE_DAY;


@Service
@Slf4j
public class JwtServiceImpl implements JwtService {

    private final MemberJwtRefreshTokenService memberJwtRefreshTokenService;
    private final ObjectMapper objectMapper;

    public JwtServiceImpl(final MemberJwtRefreshTokenService memberJwtRefreshTokenService,
                          final ObjectMapper objectMapper) {
        this.memberJwtRefreshTokenService = memberJwtRefreshTokenService;
        this.objectMapper = objectMapper;
    }

    @Override
    public LoginResponse createJwts(final LoginMember loginMember) {
        String accessToken = createToken(loginMember, ONE_HOUR.time());
        String refreshToken = createToken(loginMember, ONE_DAY.time());

        memberJwtRefreshTokenService.save(loginMember.id(), refreshToken);
        log.info("login by {}", loginMember.id());

        return LoginResponse.of(accessToken, refreshToken);
    }

    @Override
    public String createToken(final LoginMember loginMember, final long expired) {

        Date now = new Date();
        Date expiredDate = new Date( now.getTime() + expired);

        SecretKey tokenKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(JWT_KEY));

        try {
            String loginMemberJson = objectMapper.writeValueAsString(loginMember);

            return Jwts.builder()
                    .setId(UUID.randomUUID().toString())
                    .setIssuer("timeToast.com")
                    .setSubject(loginMemberJson)
                    .setIssuedAt(now)
                    .setExpiration(expiredDate)
                    .signWith(tokenKey)
                    .compact();
        }catch (JsonProcessingException e) {
            throw new InternalServerException(LOGIN_INTERCEPTOR_JSON_PROCESSING_ERROR.getMessage());
        }

    }
}
