package com.timeToast.timeToast.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.timeToast.timeToast.domain.member.LoginMember;
import com.timeToast.timeToast.global.exception.InternalServerException;
import com.timeToast.timeToast.global.jwt.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.LOGIN_INTERCEPTOR_JSON_PROCESSING_ERROR;

public class TestInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper;

    public TestInterceptor(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler){



        return true;
    }
}
