package com.timeToast.timeToast.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timeToast.timeToast.global.exception.CustomExceptionAdvice;
import com.timeToast.timeToast.global.jwt.CustomUserDetailService;
import com.timeToast.timeToast.global.jwt.JwtFilter;
import com.timeToast.timeToast.global.jwt.JwtTokenProvider;
import com.timeToast.timeToast.global.resolver.LoginMemberResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

@SpringBootTest
public class BaseIntegrationTests extends TestContainerSupport{

}
