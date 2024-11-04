package com.timeToast.timeToast.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timeToast.timeToast.global.jwt.CustomUserDetailService;
import com.timeToast.timeToast.global.jwt.JwtFilter;
import com.timeToast.timeToast.global.jwt.JwtTokenProvider;
import com.timeToast.timeToast.global.resolver.LoginMemberResolver;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public abstract class BaseControllerTests {

    protected MockMvc mockMvc;
    protected ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    protected MemberRepository memberRepository;

    @BeforeEach
    void setUp(final RestDocumentationContextProvider provider) {

        UserDetailsService customUserDetailService = new CustomUserDetailService( memberRepository, new ObjectMapper());
        JwtTokenProvider tokenProvider = new JwtTokenProvider(customUserDetailService);
        JwtFilter jwtFilter = new JwtFilter(tokenProvider);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(initController())
                .apply(SecurityMockMvcConfigurers.springSecurity(new MockSpringSecurityFilter()))
                .apply(documentationConfiguration(provider))
                .addInterceptors()
                .setCustomArgumentResolvers(new LoginMemberResolver())
                .build();
    }

    protected abstract Object initController();

}
