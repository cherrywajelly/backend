package com.timeToast.timeToast.controller.icon.icon_group;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.controller.iconGroup.IconGroupCreatorController;
import com.timeToast.timeToast.domain.enums.icon_group.IconBuiltin;
import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupPostRequest;
import com.timeToast.timeToast.service.icon.icon.IconService;
import com.timeToast.timeToast.service.icon.icon.IconServiceTest;
import com.timeToast.timeToast.service.icon.icon_group.IconGroupAdminService;
import com.timeToast.timeToast.service.icon.icon_group.IconGroupAdminServiceTest;
import com.timeToast.timeToast.util.BaseControllerTests;
import com.timeToast.timeToast.util.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.timeToast.timeToast.util.TestConstant.TEST_ACCESS_TOKEN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class IconGroupCreatorControllerTest extends BaseControllerTests {
    private final IconGroupAdminService iconGroupAdminService = new IconGroupAdminServiceTest();
    private final IconService iconService = new IconServiceTest();

    @Override
    protected Object initController() {
        return new IconGroupCreatorController(iconGroupAdminService, iconService);
    }


    @DisplayName("새로운 아이콘을 등록할 수 있다.")
    @WithMockCustomUser
    @Test
    void postIconGroup() throws Exception {
        IconGroupPostRequest iconGroupPostRequest = new IconGroupPostRequest("name", 1500, IconType.JAM, IconBuiltin.BUILTIN, "description");
        String json = objectMapper.writeValueAsString(iconGroupPostRequest);

        mockMvc.perform(
                        post("/api/v2/iconGroups")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                                .contentType(APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("아이콘 등록",
                        resource(ResourceSnippetParameters.builder()
                                .tag("아이콘")
                                .summary("새로운 아이콘 등록")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .requestFields(
                                        fieldWithPath("name").type(STRING).description("아이콘 제목"),
                                        fieldWithPath("price").type(NUMBER).description("아이콘 가격"),
                                        fieldWithPath("iconType").type(STRING).description("아이콘 타입"),
                                        fieldWithPath("iconBuiltin").type(STRING).description("기본 아이콘 여부"),
                                        fieldWithPath("description").type(STRING).description("아이콘 설명")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("메시지")
                                )
                                .build()
                        )));
    }


    @DisplayName("제작자가 제작한 아이콘 그룹 목록을 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getIconGroup() throws Exception {

        mockMvc.perform(
                        get("/api/v2/iconGroups")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("아이콘 목록 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("아이콘")
                                .summary("아이콘 목록 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("iconGroupCreatorResponses[0].iconGroupId").type(NUMBER).description("아이콘 id"),
                                        fieldWithPath("iconGroupCreatorResponses[0].iconImageUrl").type(STRING).description("아이콘 대표 이미지"),
                                        fieldWithPath("iconGroupCreatorResponses[0].iconTitle").type(STRING).description("아이콘 제목")
                                )
                                .build()
                        )));
    }

}