package com.timeToast.timeToast.controller.follow;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.domain.enums.follow.FollowType;
import com.timeToast.timeToast.service.follow.FollowService;
import com.timeToast.timeToast.service.follow.FollowServiceTest;
import com.timeToast.timeToast.util.BaseControllerTests;
import com.timeToast.timeToast.util.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.timeToast.timeToast.util.TestConstant.TEST_ACCESS_TOKEN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminFollowControllerTest extends BaseControllerTests {

    private final FollowServiceTest followService = new FollowServiceTest();

    @Override
    protected Object initController() {
        return new AdminFollowController(followService);
    }

    @DisplayName("관리자는 사용자의 팔로우 목록을 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getFollow() throws Exception {

        mockMvc.perform(
                        get("/api/v3/follows/followers/{memberId}", 1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("관리자가 사용자의 팔로우 목록 조회",
                        pathParameters(
                                parameterWithName("memberId").description("사용자 Id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("[어드민] 팔로우")
                                .summary("사용자의 팔로우 목록 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("followResponses[].memberId").type(NUMBER).description("사용자Id"),
                                        fieldWithPath("followResponses[].nickname").type(STRING).description("닉네임"),
                                        fieldWithPath("followResponses[].memberProfileUrl").type(STRING).description("사용자 프로필 url")
                                )
                                .build()
                        )));
    }

    @DisplayName("관리자는 사용자를 팔로잉 목록을 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getFollowing() throws Exception {

        mockMvc.perform(
                        get("/api/v3/follows/followings/{memberId}", 1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("관리자가 사용자의 팔로잉 목록 조회",
                        pathParameters(
                                parameterWithName("memberId").description("사용자 Id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("[어드민] 팔로우")
                                .summary("사용자 팔로잉 목록 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("followResponses[].memberId").type(NUMBER).description("사용자Id"),
                                        fieldWithPath("followResponses[].nickname").type(STRING).description("닉네임"),
                                        fieldWithPath("followResponses[].memberProfileUrl").type(STRING).description("사용자 프로필 url")
                                )
                                .build()
                        )));
    }
}
