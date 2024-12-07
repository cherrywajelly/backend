package com.timeToast.timeToast.controller.icon.icon_group;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.controller.iconGroup.IconGroupAdminController;
import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupStateRequest;
import com.timeToast.timeToast.service.icon.icon_group.IconGroupAdminService;
import com.timeToast.timeToast.service.icon.icon_group.IconGroupAdminServiceTest;
import com.timeToast.timeToast.util.BaseControllerTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.timeToast.timeToast.util.TestConstant.TEST_ACCESS_TOKEN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class IconGroupAdminControllerTest extends BaseControllerTests {
    private final IconGroupAdminService iconGroupAdminService = new IconGroupAdminServiceTest();


    @Override
    protected Object initController() {
        return new IconGroupAdminController(iconGroupAdminService);
    }


    @DisplayName("관리자는 icon 승인할 수 있다.")
    @Test
    void saveIconState() throws Exception {

        IconGroupStateRequest iconGroupStateRequest = new IconGroupStateRequest(1L, IconState.REJECTED);
        String json = objectMapper.writeValueAsString(iconGroupStateRequest);

        mockMvc.perform(
                        post("/api/v3/iconGroups")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                                .contentType(APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("관리자 아이콘 승인",
                        resource(ResourceSnippetParameters.builder()
                                .tag("아이콘")
                                .summary("관리자 아이콘 승인")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("iconGroupId").type(NUMBER).description("아이콘 그룹 id"),
                                        fieldWithPath("title").type(STRING).description("아이콘 그룹 제목"),
                                        fieldWithPath("thumbnailUrl").type(STRING).description("아이콘 그룹 썸네일 이미지"),
                                        fieldWithPath("iconType").type(STRING).description("아이콘 그룹 타입"),
                                        fieldWithPath("iconState").type(STRING).description("아이콘 state")
                                )
                                .build()
                        )));
    }


    @DisplayName("관리자 아이콘 그룹 단일 상세 조회: 성공")
    @Test
    void getManagerIconGroupDetail() throws Exception {

        mockMvc.perform(
                        get("/api/v3/iconGroups/{iconGroupId}", 1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("관리자 아이콘 그룹 단일 상세 조회",
                        pathParameters(
                                parameterWithName("iconGroupId").description("iconGroup Id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("아이콘")
                                .summary("관리자 아이콘 그룹 단일 상세 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("thumbnailImageUrl").type(STRING).description("thumbnailImage url"),
                                        fieldWithPath("title").type(STRING).description("아이콘 그룹 제목"),
                                        fieldWithPath("creatorNickname").type(STRING).description("아이콘 그룹 제작자 nickname"),
                                        fieldWithPath("price").type(NUMBER).description("아이콘 그룹 가격"),
                                        fieldWithPath("iconState").type(STRING).description("아이콘 state"),
                                        fieldWithPath("description").type(STRING).description("아이콘 description"),
                                        fieldWithPath("iconResponses[0].iconId").type(NUMBER).description("아이콘 id"),
                                        fieldWithPath("iconResponses[0].iconImageUrl").type(STRING).description("아이콘 이미지 url")
                                )
                                .build()
                        )));
    }

    @DisplayName("관리자 아이콘 그룹 전체 리스트 조회: 성공")
    @Test
    void getManagerAllIconGroup() throws Exception {

        mockMvc.perform(
                        get("/api/v3/iconGroups")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("관리자 아이콘 그룹 전체 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("아이콘")
                                .summary("관리자 아이콘 그룹 전체 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("iconGroupNonApprovalResponses[0].iconGroupId").type(NUMBER).description("아이콘 그룹 id"),
                                        fieldWithPath("iconGroupNonApprovalResponses[0].title").type(STRING).description("아이콘 그룹 제목"),
                                        fieldWithPath("iconGroupNonApprovalResponses[0].thumbnailUrl").type(STRING).description("아이콘 그룹 썸네일 이미지"),
                                        fieldWithPath("iconGroupNonApprovalResponses[0].iconType").type(STRING).description("아이콘 그룹 타입"),
                                        fieldWithPath("iconGroupNonApprovalResponses[0].iconState").type(STRING).description("아이콘 state")
                                )
                                .build()
                        )));
    }

    @DisplayName("관리자 아이콘 그룹 승인 리스트 조회: 성공")
    @Test
    void getManagerIconGroupForNonApproval() throws Exception {

        mockMvc.perform(
                        get("/api/v3/iconGroups/non-approval")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("관리자 아이콘 그룹 승인 리스트 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("아이콘")
                                .summary("관리자 아이콘 그룹 승인 리스트 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("iconGroupNonApprovalResponses[0].iconGroupId").type(NUMBER).description("아이콘 그룹 id"),
                                        fieldWithPath("iconGroupNonApprovalResponses[0].title").type(STRING).description("아이콘 그룹 제목"),
                                        fieldWithPath("iconGroupNonApprovalResponses[0].thumbnailUrl").type(STRING).description("아이콘 그룹 썸네일 이미지"),
                                        fieldWithPath("iconGroupNonApprovalResponses[0].iconType").type(STRING).description("아이콘 그룹 타입"),
                                        fieldWithPath("iconGroupNonApprovalResponses[0].iconState").type(STRING).description("아이콘 state")
                                )
                                .build()
                        )));
    }

    @DisplayName("관리자 아이콘 그룹 top 3 조회")
    @Test
    void iconGroupSummary() throws Exception {

        mockMvc.perform(
                        get("/api/v3/iconGroups/summary")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("관리자 아이콘 그룹 top 3 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("아이콘")
                                .summary("관리자 아이콘 그룹 top 3 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("iconGroupSummaries[0].title").type(STRING).description("아이콘 그룹 제목"),
                                        fieldWithPath("iconGroupSummaries[0].iconType").type(STRING).description("아이콘 그룹 타입"),
                                        fieldWithPath("iconGroupSummaries[0].count").type(NUMBER).description("아이콘 그룹 수")
                                )
                                .build()
                        )));
    }

    @DisplayName("관리자 월 별 아이콘 top 3 조회")
    @Test
    void iconGroupSummaryByYearMonth() throws Exception {

        mockMvc.perform(
                        get("/api/v3/iconGroups/summary")
                                .param("year", "2024")
                                .param("month", "1")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("관리자 월 별 아이콘 top 3 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("아이콘")
                                .summary("관리자 월 별 아이콘 top 3 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .queryParameters(
                                        parameterWithName("year").description("year"),
                                        parameterWithName("month").description("month")
                                )
                                .responseFields(
                                        fieldWithPath("iconGroupSummaries[0].title").type(STRING).description("아이콘 그룹 제목"),
                                        fieldWithPath("iconGroupSummaries[0].iconType").type(STRING).description("아이콘 그룹 타입"),
                                        fieldWithPath("iconGroupSummaries[0].count").type(NUMBER).description("아이콘 그룹 수")
                                )
                                .build()
                        )));
    }
}
