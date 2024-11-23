package com.timeToast.timeToast.controller.icon.icon_group;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.controller.iconGroup.IconGroupController;
import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.icon.icon.response.IconResponse;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.service.icon.icon_group.IconGroupService;
import com.timeToast.timeToast.service.icon.icon_group.IconGroupServiceTest;
import com.timeToast.timeToast.util.BaseControllerTests;
import com.timeToast.timeToast.util.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.timeToast.timeToast.util.TestConstant.TEST_ACCESS_TOKEN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
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
                                .tag("아이콘")
                                .summary("새로운 아이콘 그룹 구매")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .pathParameters(
                                        parameterWithName("iconGroupId").description("구매하는 아이콘 그룹 Id")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("메시지")
                                )
                                .build()
                        )));
    }

    @DisplayName("사용자의 토스트 아이콘 그룹 목록을 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getToastIconGroupsByMember() throws Exception {

        mockMvc.perform(
                        get("/api/v1/iconGroups/members/toasts")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("사용자의 토스트 아이콘 그룹 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("아이콘")
                                .summary("사용자 토스트 아이콘 그룹 목록 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("iconGroupResponses[0].iconGroupId").type(NUMBER).description("아이콘 그룹 id"),
                                        fieldWithPath("iconGroupResponses[0].name").type(STRING).description("아이콘 그룹 제목"),
                                        fieldWithPath("iconGroupResponses[0].icon[].iconId").type(NUMBER).description("아이콘 그룹내 아이콘 id"),
                                        fieldWithPath("iconGroupResponses[0].icon[].iconImageUrl").type(STRING).description("아이콘 그룹내 아이콘 이미지")
                                )
                                .build()
                        )));
    }

    @DisplayName("사용자의 잼 아이콘 그룹 목록을 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getJamIconGroupsByMember() throws Exception {

        mockMvc.perform(
                        get("/api/v1/iconGroups/members/jams")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("사용자의 잼 아이콘 그룹 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("아이콘")
                                .summary("사용자 잼 아이콘 그룹 목록 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("iconGroupResponses[0].iconGroupId").type(NUMBER).description("아이콘 그룹 id"),
                                        fieldWithPath("iconGroupResponses[0].name").type(STRING).description("아이콘 그룹 제목"),
                                        fieldWithPath("iconGroupResponses[0].icon[].iconId").type(NUMBER).description("아이콘 그룹내 아이콘 id"),
                                        fieldWithPath("iconGroupResponses[0].icon[].iconImageUrl").type(STRING).description("아이콘 그룹내 아이콘 이미지")
                                )
                                .build()
                        )));
    }

    @DisplayName("마켓의 토스트 아이콘 그룹 목록을 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getAllToastsIconGroups() throws Exception {

        mockMvc.perform(
                        get("/api/v1/iconGroups/toasts")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("마켓의 토스트 아이콘 그룹 목록을 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("아이콘")
                                .summary("마켓의 토스트 아이콘 그룹 목록을 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("iconGroupMarketResponses[0].iconGroupId").type(NUMBER).description("아이콘 그룹 id"),
                                        fieldWithPath("iconGroupMarketResponses[0].thumbnailImageUrl").type(STRING).description("썸네일 이미지 url"),
                                        fieldWithPath("iconGroupMarketResponses[0].title").type(STRING).description("아이콘 그룹 제목"),
                                        fieldWithPath("iconGroupMarketResponses[0].creatorNickname").type(STRING).description("아이콘 그룹 제작자 nickname"),
                                        fieldWithPath("iconGroupMarketResponses[0].iconType").type(STRING).description("아이콘 그룹 타입"),
                                        fieldWithPath("iconGroupMarketResponses[0].isBuy").type(BOOLEAN).description("사용자 구입 여부")

                                )
                                .build()
                        )));
    }

    @DisplayName("마켓의 잼 아이콘 그룹 목록을 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getAllJamsIconGroups() throws Exception {

        mockMvc.perform(
                        get("/api/v1/iconGroups/jams")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("마켓의 토스트 아이콘 그룹 목록을 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("아이콘")
                                .summary("마켓의 토스트 아이콘 그룹 목록을 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("iconGroupMarketResponses[0].iconGroupId").type(NUMBER).description("아이콘 그룹 id"),
                                        fieldWithPath("iconGroupMarketResponses[0].thumbnailImageUrl").type(STRING).description("썸네일 이미지 url"),
                                        fieldWithPath("iconGroupMarketResponses[0].title").type(STRING).description("아이콘 그룹 제목"),
                                        fieldWithPath("iconGroupMarketResponses[0].creatorNickname").type(STRING).description("아이콘 그룹 제작자 nickname"),
                                        fieldWithPath("iconGroupMarketResponses[0].iconType").type(STRING).description("아이콘 그룹 타입"),
                                        fieldWithPath("iconGroupMarketResponses[0].isBuy").type(BOOLEAN).description("사용자 구입 여부")

                                )
                                .build()
                        )));
    }

    @DisplayName("아이콘 그룹 단일 상세 조회")
    @WithMockCustomUser
    @Test
    void getIconGroupDetail() throws Exception {

        mockMvc.perform(
                        get("/api/v1/iconGroups/{iconGroupId}", 1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("아이콘 그룹 단일 상세 조회",
                        pathParameters(
                                parameterWithName("iconGroupId").description("iconGroup Id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("아이콘")
                                .summary("아이콘 그룹 단일 상세 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("thumbnailImageUrl").type(STRING).description("thumbnailImage url"),
                                        fieldWithPath("title").type(STRING).description("아이콘 그룹 제목"),
                                        fieldWithPath("creatorNickname").type(STRING).description("아이콘 그룹 제작자 nickname"),
                                        fieldWithPath("price").type(NUMBER).description("아이콘 그룹 가격"),
                                        fieldWithPath("iconResponses[0].iconId").type(NUMBER).description("아이콘 id"),
                                        fieldWithPath("iconResponses[0].iconImageUrl").type(STRING).description("아이콘 이미지 url")
                                )
                                .build()
                        )));
    }


    @DisplayName("아이콘 그룹 목록을 삭제할 수 있다.")
    @WithMockCustomUser
    @Test
    public void deleteIconGroup() throws Exception {

        mockMvc.perform(
                        delete("/api/v1/iconGroups/{iconGroupId}", 1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("아이콘 그룹 삭제",
                        resource(ResourceSnippetParameters.builder()
                                .tag("아이콘")
                                .summary("아이콘 그룹 목록 삭제")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .pathParameters(
                                        parameterWithName("iconGroupId").description("삭제할 아이콘 그룹 Id")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("메시지")
                                )
                                .build()
                        )));
    }
}
