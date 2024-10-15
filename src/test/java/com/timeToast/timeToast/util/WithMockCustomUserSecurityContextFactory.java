package com.timeToast.timeToast.util;

import com.timeToast.timeToast.domain.member.Member;
import com.timeToast.timeToast.global.jwt.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser annotation) {
        final SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

        CustomUserDetails userDetails = new CustomUserDetails(
                Member.builder()
                .email(annotation.userEmail())
                .nickname(annotation.userNickname())
                .memberRole(annotation.userRole()).build()
        );

        final UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken((UserDetails)userDetails,
                null,
                userDetails.getAuthorities());

        securityContext.setAuthentication(authenticationToken);
        return securityContext;
    }
}
