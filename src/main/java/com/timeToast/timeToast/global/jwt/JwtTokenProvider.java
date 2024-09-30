package com.timeToast.timeToast.global.jwt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Value;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;

import static com.timeToast.timeToast.global.constant.JwtKey.Jwt_Key;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;

    public JwtTokenProvider(final UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public String createToken(final long memberId, final long expired){

        Date now = new Date();
        Date expiredDate = new Date( now.getTime() + expired);

        SecretKey tokenKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(Jwt_Key));

        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setIssuer("timeToast.com")
                .setSubject(String.valueOf(memberId))
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(tokenKey)
                .compact();

    }

    public String resolveToken(HttpServletRequest request){
        return request.getHeader(AUTHORIZATION);
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserPk(String token) {
        SecretKey tokenKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(Jwt_Key));
        return Jwts.parser().setSigningKey(tokenKey).parseClaimsJws(token).getBody().getSubject();
    }


    public boolean validateToken(String token) {
        try {
            SecretKey tokenKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(Jwt_Key));
            // Bearer 검증
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(tokenKey).build().parseClaimsJws(token);
            // 만료되었을 시 false
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
