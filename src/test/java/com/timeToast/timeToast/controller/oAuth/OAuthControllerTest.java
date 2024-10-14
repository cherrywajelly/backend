package com.timeToast.timeToast.controller.oAuth;

import com.timeToast.timeToast.global.resolver.LoginMemberResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;

import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;

import static com.timeToast.timeToast.util.TestConstant.TEST_AUTH_CODE;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OAuthControllerTest {

    protected MockMvc mockMvc;

    @BeforeEach
    void setUp(final RestDocumentationContextProvider provider) {
        this.mockMvc = MockMvcBuilders.standaloneSetup()
//                .setCustomArgumentResolvers(new LoginMemberResolver())
                .apply(documentationConfiguration(provider))
                .build();
    }
    @DisplayName("인가 코드를 사용하여 카카오 로그인에 성공한다.")
    @WithMockUser(username = "User", roles = "USER")
    @Test()
    void kakaoLogin() throws Exception {

        mockMvc.perform(
                        post("/api/v1/login/kakao")
                                .with(SecurityMockMvcRequestPostProcessors.user("shane"))
                                .param("code", TEST_AUTH_CODE.value())
                )
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document("카카오 로그인 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("로그인")
                                .summary("카카오 로그인")
                                .responseFields(
                                        fieldWithPath("accessToken").type(STRING).description("access token"),
                                        fieldWithPath("refreshToken").type(STRING).description("access token")
                                )
                                .build()
                        )));
    }
}