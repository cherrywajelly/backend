package com.timeToast.timeToast.global.config;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;

import static com.timeToast.timeToast.global.constant.JwtKey.Jwt_Key;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private SecretKey jwtKey;

    @PostConstruct
    protected void init(){
        jwtKey = Keys.hmacShaKeyFor(Base64.getEncoder().encodeToString(Jwt_Key.getBytes()).getBytes());
    }

    public String createToken(final long memberId, final long expired){

        Date now = new Date();
        Date expiredDate = new Date( now.getTime() + expired);

        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setIssuer("timeToast.com")
                .setSubject(String.valueOf(memberId))
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(jwtKey)
                .compact();

    }

}
