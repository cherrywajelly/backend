package com.timeToast.timeToast.util;

import com.timeToast.timeToast.domain.enums.member.MemberRole;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomManager {
    long userId() default 2L;
    String userEmail() default "test email";
    String userNickname() default "test nickname";
    MemberRole userRole() default MemberRole.MANAGER;

}
