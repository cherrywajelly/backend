package com.timeToast.timeToast.controller.follow;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.service.follow.FollowService;
import com.timeToast.timeToast.service.follow.FollowServiceTest;
import com.timeToast.timeToast.util.BaseControllerTests;
import com.timeToast.timeToast.util.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.timeToast.timeToast.util.TestConstant.TEST_ACCESS_TOKEN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FollowerControllerTest extends BaseControllerTests {

    private final FollowService followService = new FollowServiceTest();

    @Override
    protected Object initController() {
        return new FollowController(followService);
    }


    @DisplayName("특정 사용자를 memberId를 활용하여 팔로우 할 수 있다.")
    @WithMockCustomUser
    @Test
    void createFollow() throws Exception {

        mockMvc.perform(
                        post("/api/v1/follows/following/{followingId}", 2)
                                .header(AUTHORIZATION, TEST_ACCESS_TOKEN.value())
                )
                .andExpect(status().isOk())
                .andDo(document("팔로우 정보 저장",
                        resource(ResourceSnippetParameters.builder()
                                .tag("팔로우")
                                .summary("특정 사용자를 팔로우 하기")
                                .build()
                        )));
    }

    @DisplayName("로그인한 사용자의 팔로잉 리스트를 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void findFollowingList() throws Exception {

        mockMvc.perform(
                        get("/api/v1/follows/followings")
                                .header(AUTHORIZATION, TEST_ACCESS_TOKEN.value())
                )
                .andExpect(status().isOk())
                .andDo(document("팔로잉 리스트 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("팔로우")
                                .summary("로그인한 사용자의 팔로잉 리스트 조회")
                                .responseFields(
                                        fieldWithPath("followResponses[0].memberId").type(NUMBER).description("사용자Id"),
                                        fieldWithPath("followResponses[0].nickname").type(STRING).description("닉네임"),
                                        fieldWithPath("followResponses[0].memberProfileUrl").type(STRING).description("사용자 프로필 url")
                                )
                                .build()

                        )));
    }

    @DisplayName("로그인한 사용자의 팔로워 리스트를 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void findFollowerList() throws Exception {

        mockMvc.perform(
                        get("/api/v1/follows/followers")
                                .header(AUTHORIZATION, TEST_ACCESS_TOKEN.value())
                )
                .andExpect(status().isOk())
                .andDo(document("팔로워 리스트 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("팔로우")
                                .summary("로그인한 사용자의 팔로워 리스트 조회")
                                .responseFields(
                                        fieldWithPath("followResponses[0].memberId").type(NUMBER).description("사용자Id"),
                                        fieldWithPath("followResponses[0].nickname").type(STRING).description("닉네임"),
                                        fieldWithPath("followResponses[0].memberProfileUrl").type(STRING).description("사용자 프로필 url")
                                )
                                .build()

                        )));
    }

    @DisplayName("로그인한 사용자의 팔로잉을 삭제할 수 있다.")
    @WithMockCustomUser
    @Test
    void deleteFollowing() throws Exception {

        mockMvc.perform(
                        delete("/api/v1/follows/following/{followingMemberId}",1)
                                .header(AUTHORIZATION, TEST_ACCESS_TOKEN.value())
                )
                .andExpect(status().isOk())
                .andDo(document("팔로잉 삭제",
                        resource(ResourceSnippetParameters.builder()
                                .tag("팔로우")
                                .summary("로그인한 사용자의 팔로잉 삭제")
                                .build()

                        )));
    }

    @DisplayName("로그인한 사용자가 자신의 팔로워 삭제할 수 있다.")
    @WithMockCustomUser
    @Test
    void deleteFollower() throws Exception {

        mockMvc.perform(
                        delete("/api/v1/follows/follower/{followerMemberId}",1)
                                .header(AUTHORIZATION, TEST_ACCESS_TOKEN.value())
                )
                .andExpect(status().isOk())
                .andDo(document("팔로워 삭제",
                        resource(ResourceSnippetParameters.builder()
                                .tag("팔로우")
                                .summary("로그인한 사용자가 팔로워 삭제")
                                .build()

                        )));
    }



}
