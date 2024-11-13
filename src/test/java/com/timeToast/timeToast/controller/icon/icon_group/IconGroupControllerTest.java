package com.timeToast.timeToast.controller.icon.icon_group;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.controller.iconGroup.IconGroupController;
import com.timeToast.timeToast.dto.icon.icon.response.IconResponse;
import com.timeToast.timeToast.service.icon.icon_group.IconGroupService;
import com.timeToast.timeToast.service.icon.icon_group.IconGroupServiceTest;
import com.timeToast.timeToast.util.BaseControllerTests;
import com.timeToast.timeToast.util.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.timeToast.timeToast.util.TestConstant.TEST_ACCESS_TOKEN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class IconGroupControllerTest extends BaseControllerTests {
    private final IconGroupService iconGroupService = new IconGroupServiceTest();

    @Override
    protected Object initController() {
        return new IconGroupController(iconGroupService);
    }

    @DisplayName("아이콘 그룹을 구매할 수 있다.")
    @WithMockCustomUser
    @Test
    void buyIconGroup() throws Exception {

        mockMvc.perform(
                        post("/api/v1/iconGroups/members/{iconGroupId}", 1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("아이콘 그룹 구매",
                        resource(ResourceSnippetParameters.builder()
                                .tag("아이콘 그룹")
                                .summary("새로운 아이콘 그룹 구매")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .pathParameters(
                                        parameterWithName("iconGroupId").description("구매하는 아이콘 그룹 Id")
                                )
                                .build()
                        )));
    }

    @DisplayName("아이콘 그룹 목록을 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getIconGroups() throws Exception {

        mockMvc.perform(
                        get("/api/v1/iconGroups")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("아이콘 그룹 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("아이콘 그룹")
                                .summary("아이콘 그룹 목록 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("[].iconGroupId").type(NUMBER).description("아이콘 그룹 id"),
                                        fieldWithPath("[].name").type(STRING).description("아이콘 그룹 제목"),
                                        fieldWithPath("[].icon[].iconId").type(NUMBER).description("아이콘 그룹내 아이콘 id"),
                                        fieldWithPath("[].icon[].iconImageUrl").type(STRING).description("아이콘 그룹내 아이콘 이미지")
                                )
                                .build()
                        )));
    }
}
//
//
//@DeleteMapping("/{iconGroupId}")
//public void deleteIconGroup(@Login LoginMember loginMember, @PathVariable("iconGroupId") final long iconGroupId) {
//    iconGroupService.deleteIconGroup(loginMember.id(), iconGroupId);
//}