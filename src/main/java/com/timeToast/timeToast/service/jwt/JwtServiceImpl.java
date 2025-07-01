package com.timeToast.timeToast.service.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.domain.member.member_token.MemberToken;
import com.timeToast.timeToast.dto.member.LoginResponse;
import com.timeToast.timeToast.global.exception.InternalServerException;
import com.timeToast.timeToast.global.exception.UnauthorizedException;
import com.timeToast.timeToast.global.jwt.JwtTokenProvider;
import com.timeToast.timeToast.repository.redis.RedisRepository;
import com.timeToast.timeToast.repository.redis.member_token.MemberTokenRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.LOGIN_INTERCEPTOR_JSON_PROCESSING_ERROR;
import static com.timeToast.timeToast.global.constant.ExceptionConstant.REFRESH_TOKEN_EXPIRED;
import static com.timeToast.timeToast.global.constant.JwtExp.ACCESS_EXP;
import static com.timeToast.timeToast.global.constant.JwtExp.REFRESH_EXP;
import static com.timeToast.timeToast.global.constant.JwtKey.JWT_KEY;


@Service
@Slf4j
public class JwtServiceImpl implements JwtService {

    private final MemberTokenRepository memberTokenRepository;
    private final RedisRepository redisRepository;
    private final ObjectMapper objectMapper;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtServiceImpl(final MemberTokenRepository memberTokenRepository, final RedisRepository redisRepository,
                          final ObjectMapper objectMapper, final JwtTokenProvider jwtTokenProvider) {
        this.memberTokenRepository = memberTokenRepository;
        this.redisRepository = redisRepository;
        this.objectMapper = objectMapper;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional
    @Override
    public LoginResponse createJwts(final LoginMember loginMember, final boolean isNew) {
        String accessToken = createToken(loginMember, ACCESS_EXP);
        String refreshToken = createToken(loginMember, REFRESH_EXP);
        MemberToken memberToken = memberTokenRepository.save(new MemberToken(loginMember.id(), refreshToken));
        redisRepository.setExpire(getKey(memberToken), Duration.ofMillis(REFRESH_EXP));
        log.info("login by {}", loginMember.id());
        return LoginResponse.of(accessToken, refreshToken, isNew);
    }

    private String getKey(final MemberToken memberToken){
        return "token:"+memberToken.getMemberId();
    }


    private String createToken(final LoginMember loginMember, final long expired) {

        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + expired);

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

    @Transactional
    @Override
    public LoginResponse tokenRenewal(final String refreshToken) {
        if(jwtTokenProvider.validateToken(refreshToken)){
            String claims = jwtTokenProvider.getUserClaims(refreshToken);

            try {
                LoginMember loginMember = objectMapper.readValue(claims, LoginMember.class);
                return createJwts(loginMember, false);

            } catch (JsonProcessingException e) {
                throw new InternalServerException(LOGIN_INTERCEPTOR_JSON_PROCESSING_ERROR.getMessage());
            }

        }else {
            throw new UnauthorizedException(REFRESH_TOKEN_EXPIRED.getMessage());
        }
    }
}
