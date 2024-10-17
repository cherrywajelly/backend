package com.timeToast.timeToast.util;

import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.member.LoginMember;
import com.timeToast.timeToast.global.jwt.CustomUserDetails;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

public class MockSpringSecurityFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
//        SecurityContextHolder.getContext()
//                .setAuthentication((Authentication) ((HttpServletRequest) req).getUserPrincipal());
        req.setAttribute("LoginMember", LoginMember.builder().id(1).email("test email").role(MemberRole.USER).build());
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
        SecurityContextHolder.clearContext();
    }
    public void getFilters(MockHttpServletRequest mockHttpServletRequest){}

}
