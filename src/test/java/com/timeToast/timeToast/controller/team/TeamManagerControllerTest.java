package com.timeToast.timeToast.controller.team;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.service.team.TeamService;
import com.timeToast.timeToast.service.team.TeamServiceTest;
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
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TeamManagerControllerTest extends BaseControllerTests {
    private final TeamService teamService = new TeamServiceTest();

    @Override
    protected Object initController() {
        return new TeamManagerController(teamService);
    }

    @DisplayName("관리자는 그룹 목록을 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getTeamsManager() throws Exception {

        mockMvc.perform(
                        get("/api/v3/teams")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("관리자 그룹 목록 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("관리자 그룹")
                                .summary("관리자 그룹 목록 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("teamManagerResponses[0].teamId").type(NUMBER).description("그룹 id"),
                                        fieldWithPath("teamManagerResponses[0].teamProfileUrl").type(STRING).description("그룹 프로필 이미지"),
                                        fieldWithPath("teamManagerResponses[0].name").type(STRING).description("그룹 이름"),
                                        fieldWithPath("teamManagerResponses[0].createdAt").type(STRING).description("그룹 생성 날짜"),
                                        fieldWithPath("teamManagerResponses[0].memberCount").type(NUMBER).description("그룹 소속 인원수")
                                )
                                .build()
                        )));
    }

    @DisplayName("관리자는 그룹을 상세 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getTeamInfoManager() throws Exception {

        mockMvc.perform(
                        get("/api/v3/teams/{teamId}", 1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("관리자 그룹 상세 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("관리자 그룹")
                                .summary("관리자 그룹 상세 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("teamId").type(NUMBER).description("그룹 id"),
                                        fieldWithPath("teamProfileUrl").type(STRING).description("그룹 프로필 이미지"),
                                        fieldWithPath("name").type(STRING).description("그룹 이름"),
                                        fieldWithPath("createdAt").type(STRING).description("그룹 생성 날짜"),
                                        fieldWithPath("managerProfileResponses[0].nickname").type(STRING).description("그룹 소속자 닉네임"),
                                        fieldWithPath("managerProfileResponses[0].memberProfileUrl").type(STRING).description("그룹 소속자 프로필 이미지 url")
                                )
                                .build()
                        )));
    }
}
