package com.timeToast.timeToast.controller.member.oauth;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.service.member.oauth.OAuthService;
import com.timeToast.timeToast.service.member.oauth.OAuthServiceTest;
import com.timeToast.timeToast.util.BaseControllerTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.timeToast.timeToast.util.TestConstant.TEST_AUTH_CODE;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OAuthAdminControllerTest extends BaseControllerTests {

    private final OAuthService oAuthService = new OAuthServiceTest();

    @Override
    protected Object initController() {
        return new OAuthAdminController(oAuthService);
    }

    @DisplayName("관리자는 인가 코드를 사용하여 카카오 로그인에 성공한다.")
    @Test()
    void kakaoLogin() throws Exception {

        mockMvc.perform(
                        get("/api/v3/login/kakao")
                                .param("code", TEST_AUTH_CODE.value())
                )
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document("카카오 로그인 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("로그인")
                                .summary("관리자 카카오 로그인")
                                .responseFields(
                                        fieldWithPath("accessToken").type(STRING).description("access token"),
                                        fieldWithPath("refreshToken").type(STRING).description("access token"),
                                        fieldWithPath("isNew").type(BOOLEAN).description("신규 가입 여부")

                                )
                                .build()
                        )));
    }

    @DisplayName("관리자는 인가 코드를 사용하여 구글 로그인에 성공한다.")
    @Test()
    void googleLogin() throws Exception {

        mockMvc.perform(
                        get("/api/v3/login/google")
                                .param("code", TEST_AUTH_CODE.value())
                )
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document("구글 로그인 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("로그인")
                                .summary("관리자 구글 로그인")
                                .responseFields(
                                        fieldWithPath("accessToken").type(STRING).description("access token"),
                                        fieldWithPath("refreshToken").type(STRING).description("access token"),
                                        fieldWithPath("isNew").type(BOOLEAN).description("신규 가입 여부")
                                )
                                .build()
                        )));
    }
}