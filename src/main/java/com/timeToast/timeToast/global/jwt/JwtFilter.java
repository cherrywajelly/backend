package com.timeToast.timeToast.global.jwt;

import com.timeToast.timeToast.domain.member.LoginMember;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtFilter (final JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //get jwt token to header
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

        System.out.println("jwt token: " + token);

        if(token != null){
            if(token.startsWith("Bearer ")){
                token = token.substring(7);
            }
        }

        //token 유효성 검사
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 토큰으로부터 유저 정보를 받아
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            // SecurityContext 에 객체 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            request.setAttribute("LoginMember", LoginMember.from(customUserDetails.getMember()));
            System.out.println(request.getAttributeNames());


        }

        // 다음 Filter 실행
        filterChain.doFilter(request, response);
    }
}
