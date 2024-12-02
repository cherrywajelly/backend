package com.timeToast.timeToast.controller.team;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.dto.member_group.request.TeamSaveRequest;
import com.timeToast.timeToast.service.team.TeamService;
import com.timeToast.timeToast.service.team.TeamServiceTest;
import com.timeToast.timeToast.util.BaseControllerTests;
import com.timeToast.timeToast.util.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.timeToast.timeToast.util.TestConstant.TEST_ACCESS_TOKEN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

public class TeamControllerTest extends BaseControllerTests {

    private final TeamService teamService = new TeamServiceTest();
    @Override
    protected Object initController() {
        return new TeamController(teamService);
    }

    @DisplayName("새로운 팀을 생성할 수 있다.")
    @WithMockCustomUser
    @Test
    void saveTeam() throws Exception {
        TeamSaveRequest teamSaveRequest = new TeamSaveRequest("team1", List.of(1L,2L));
        String json = objectMapper.writeValueAsString(teamSaveRequest);

        mockMvc.perform(
                        post("/api/v1/teams")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                                .contentType(APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("팀 생성하기",
                        resource(ResourceSnippetParameters.builder()
                                .tag("팀")
                                .summary("팀 생성")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .requestFields(
                                        fieldWithPath("teamName").type(STRING).description("팀 이름"),
                                        fieldWithPath("teamMembers").type(ARRAY).description("팀 구성원 memberId list")
                                )
                                .responseFields(
                                        fieldWithPath("teamId").type(NUMBER).description("팀 id"),
                                        fieldWithPath("teamName").type(STRING).description("팀 이름"),
                                        fieldWithPath("teamProfileUrl").type(STRING).description("팀 profile url")
                                )
                                .build()
                        )));
    }

    @DisplayName("팀 프로필 사진을 변경할 수 있다.")
    @WithMockCustomUser
    @Test
    void saveProfileImage() throws Exception {

        mockMvc.perform(
                        multipart("/api/v1/teams/{teamId}/image",1)
                                .file("teamProfileImage", "hello.png".getBytes())
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(status().isOk())
                .andDo(document("팀 프로필 사진 저장",
                        requestParts(
                                partWithName("teamProfileImage").description("팀 프로필 이미지 파일")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("팀")
                                .summary("팀 프로필 사진 저장")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("teamId").type(NUMBER).description("팀 id"),
                                        fieldWithPath("teamName").type(STRING).description("팀 이름"),
                                        fieldWithPath("teamProfileUrl").type(STRING).description("팀 profile url")
                                )
                                .build()
                        )));
    }

    @DisplayName("팀 프로필 사진을 변경할 수 있다. - 실패: 팀 조회 실패")
    @WithMockCustomUser
    @Test
    void saveProfileImageFail() throws Exception {

        mockMvc.perform(
                        multipart("/api/v1/teams/{teamId}/image",2)
                                .file("teamProfileImage", "hello.png".getBytes())
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(status().isNotFound())
                .andDo(document("팀 프로필 사진 저장",
                        requestParts(
                                partWithName("teamProfileImage").description("팀 프로필 이미지 파일")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("팀")
                                .summary("팀 프로필 사진 저장")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("해당 그룹 정보를 찾을 수 없습니다.")
                                )
                                .build()
                        )));
    }

    @DisplayName("로그인한 사용자의 팀 목록을 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void findTeamList() throws Exception {

        mockMvc.perform(
                        get("/api/v1/teams")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("로그인한 사용자의 팀 목록 조회하기",
                        resource(ResourceSnippetParameters.builder()
                                .tag("팀")
                                .summary("팀 목록 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("teamResponses[0].teamId").type(NUMBER).description("팀 id"),
                                        fieldWithPath("teamResponses[0].teamName").type(STRING).description("팀 이름"),
                                        fieldWithPath("teamResponses[0].teamProfileUrl").type(STRING).description("팀 프로필 이미지 url")
                                )
                                .build()
                        )));
    }

    @DisplayName("로그인한 사용자는 자신의 팀을 삭제할 수 있다.")
    @WithMockCustomUser
    @Test
    void deleteTeam() throws Exception {

        mockMvc.perform(
                        delete("/api/v1/teams/{teamId}", 1)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("로그인한 사용자의 팀 삭제하기",
                        pathParameters(
                                parameterWithName("teamId").description("team id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("팀")
                                .summary("사용자 팀 삭제")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("메시지")
                                )
                                .build()
                        )));
    }

    @DisplayName("로그인한 사용자는 자신의 팀을 삭제할 수 있다. - 실패: 팀 멤버 조회 실패")
    @WithMockCustomUser
    @Test
    void deleteTeamFail() throws Exception {

        mockMvc.perform(
                        delete("/api/v1/teams/{teamId}", 2)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isNotFound())
                .andDo(document("로그인한 사용자의 팀 삭제하기 실패",
                        pathParameters(
                                parameterWithName("teamId").description("team id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("팀")
                                .summary("사용자 팀 삭제")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("사용자의 그룹에서 해당 그룹 정보를 찾을 수 없습니다.")
                                )
                                .build()
                        )));
    }
}
