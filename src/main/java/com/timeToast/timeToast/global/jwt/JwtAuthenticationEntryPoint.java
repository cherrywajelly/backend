package com.timeToast.timeToast.global.jwt;

import com.timeToast.timeToast.global.exception.UnauthorizedException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.INVALID_USER;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        throw new UnauthorizedException(INVALID_USER.getMessage());
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
