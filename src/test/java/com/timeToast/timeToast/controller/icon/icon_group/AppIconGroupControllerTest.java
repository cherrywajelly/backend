package com.timeToast.timeToast.controller.icon.icon_group;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.controller.icon.AppIconGroupController;
import com.timeToast.timeToast.service.icon.IconService;
import com.timeToast.timeToast.service.icon.icon_group.IconServiceTest;
import com.timeToast.timeToast.util.BaseControllerTests;
import com.timeToast.timeToast.util.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.timeToast.timeToast.util.TestConstant.TEST_ACCESS_TOKEN;
import static java.sql.JDBCType.ARRAY;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AppIconGroupControllerTest extends BaseControllerTests {
    private final IconService iconService = new IconServiceTest();

    @Override
    protected Object initController() {
        return new AppIconGroupController(iconService);
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
                                .tag("[앱] 아이콘")
                                .summary("사용자 토스트 아이콘 그룹 목록 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("iconGroupDetailResponses[].iconGroupDetail.iconGroupSummaryInfo.iconGroupId").type(NUMBER).description("아이콘 그룹 iconGroupId"),
                                        fieldWithPath("iconGroupDetailResponses[].iconGroupDetail.iconGroupSummaryInfo.title").type(STRING).description("아이콘 그룹 제목"),
                                        fieldWithPath("iconGroupDetailResponses[].iconGroupDetail.iconGroupSummaryInfo.creatorNickname").type(STRING).description("아이콘 제작자 닉네임"),
                                        fieldWithPath("iconGroupDetailResponses[].iconGroupDetail.iconGroupSummaryInfo.thumbnailImageUrl").type(STRING).description("아이콘 그룹 thumbnailImage url"),
                                        fieldWithPath("iconGroupDetailResponses[].iconGroupDetail.iconGroupSummaryInfo.description").type(STRING).description("아이콘 그룹 설명"),
                                        fieldWithPath("iconGroupDetailResponses[].iconGroupDetail.iconGroupSummaryInfo.iconType").type(STRING).description("아이콘 그룹 타입"),
                                        fieldWithPath("iconGroupDetailResponses[].iconGroupDetail.iconGroupSummaryInfo.iconState").type(STRING).description("아이콘 그룹 상태"),
                                        fieldWithPath("iconGroupDetailResponses[].iconGroupDetail.iconGroupSummaryInfo.price").type(NUMBER).description("아이콘 그룹 가격"),
                                        fieldWithPath("iconGroupDetailResponses[].iconGroupDetail.icons[].iconId").type(NUMBER).description("아이콘 Id"),
                                        fieldWithPath("iconGroupDetailResponses[].iconGroupDetail.icons[].iconImageUrl").type(STRING).description("아이콘 image url"),
                                        fieldWithPath("iconGroupDetailResponses[].isBuy").type(BOOLEAN).description("구매 여부")
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
                                .tag("[앱] 아이콘")
                                .summary("사용자 잼 아이콘 그룹 목록 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("iconGroupDetailResponses[].iconGroupDetail.iconGroupSummaryInfo.iconGroupId").type(NUMBER).description("아이콘 그룹 iconGroupId"),
                                        fieldWithPath("iconGroupDetailResponses[].iconGroupDetail.iconGroupSummaryInfo.title").type(STRING).description("아이콘 그룹 제목"),
                                        fieldWithPath("iconGroupDetailResponses[].iconGroupDetail.iconGroupSummaryInfo.creatorNickname").type(STRING).description("아이콘 제작자 닉네임"),
                                        fieldWithPath("iconGroupDetailResponses[].iconGroupDetail.iconGroupSummaryInfo.thumbnailImageUrl").type(STRING).description("아이콘 그룹 thumbnailImage url"),
                                        fieldWithPath("iconGroupDetailResponses[].iconGroupDetail.iconGroupSummaryInfo.description").type(STRING).description("아이콘 그룹 설명"),
                                        fieldWithPath("iconGroupDetailResponses[].iconGroupDetail.iconGroupSummaryInfo.iconType").type(STRING).description("아이콘 그룹 타입"),
                                        fieldWithPath("iconGroupDetailResponses[].iconGroupDetail.iconGroupSummaryInfo.iconState").type(STRING).description("아이콘 그룹 상태"),
                                        fieldWithPath("iconGroupDetailResponses[].iconGroupDetail.iconGroupSummaryInfo.price").type(NUMBER).description("아이콘 그룹 가격"),
                                        fieldWithPath("iconGroupDetailResponses[].iconGroupDetail.icons[].iconId").type(NUMBER).description("아이콘 Id"),
                                        fieldWithPath("iconGroupDetailResponses[].iconGroupDetail.icons[].iconImageUrl").type(STRING).description("아이콘 image url"),
                                        fieldWithPath("iconGroupDetailResponses[].isBuy").type(BOOLEAN).description("구매 여부")
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
                                .tag("[앱] 아이콘")
                                .summary("마켓의 토스트 아이콘 그룹 목록을 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("iconGroupInfoResponses[].iconGroupSummaryInfo.iconGroupId").type(NUMBER).description("아이콘 그룹 iconGroupId"),
                                        fieldWithPath("iconGroupInfoResponses[].iconGroupSummaryInfo.title").type(STRING).description("아이콘 그룹 제목"),
                                        fieldWithPath("iconGroupInfoResponses[].iconGroupSummaryInfo.creatorNickname").type(STRING).description("아이콘 제작자 닉네임"),
                                        fieldWithPath("iconGroupInfoResponses[].iconGroupSummaryInfo.thumbnailImageUrl").type(STRING).description("아이콘 그룹 thumbnailImage url"),
                                        fieldWithPath("iconGroupInfoResponses[].iconGroupSummaryInfo.description").type(STRING).description("아이콘 그룹 설명"),
                                        fieldWithPath("iconGroupInfoResponses[].iconGroupSummaryInfo.iconType").type(STRING).description("아이콘 그룹 타입"),
                                        fieldWithPath("iconGroupInfoResponses[].iconGroupSummaryInfo.iconState").type(STRING).description("아이콘 그룹 상태"),
                                        fieldWithPath("iconGroupInfoResponses[].iconGroupSummaryInfo.price").type(NUMBER).description("아이콘 그룹 가격"),
                                        fieldWithPath("iconGroupInfoResponses[].isBuy").type(BOOLEAN).description("구매 여부")

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
                                .tag("[앱] 아이콘")
                                .summary("마켓의 토스트 아이콘 그룹 목록을 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("iconGroupInfoResponses[].iconGroupSummaryInfo.iconGroupId").type(NUMBER).description("아이콘 그룹 iconGroupId"),
                                        fieldWithPath("iconGroupInfoResponses[].iconGroupSummaryInfo.title").type(STRING).description("아이콘 그룹 제목"),
                                        fieldWithPath("iconGroupInfoResponses[].iconGroupSummaryInfo.creatorNickname").type(STRING).description("아이콘 제작자 닉네임"),
                                        fieldWithPath("iconGroupInfoResponses[].iconGroupSummaryInfo.thumbnailImageUrl").type(STRING).description("아이콘 그룹 thumbnailImage url"),
                                        fieldWithPath("iconGroupInfoResponses[].iconGroupSummaryInfo.description").type(STRING).description("아이콘 그룹 설명"),
                                        fieldWithPath("iconGroupInfoResponses[].iconGroupSummaryInfo.iconType").type(STRING).description("아이콘 그룹 타입"),
                                        fieldWithPath("iconGroupInfoResponses[].iconGroupSummaryInfo.iconState").type(STRING).description("아이콘 그룹 상태"),
                                        fieldWithPath("iconGroupInfoResponses[].iconGroupSummaryInfo.price").type(NUMBER).description("아이콘 그룹 가격"),
                                        fieldWithPath("iconGroupInfoResponses[].isBuy").type(BOOLEAN).description("구매 여부")

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
                                .tag("[앱] 아이콘")
                                .summary("아이콘 그룹 단일 상세 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("iconGroupDetail.iconGroupSummaryInfo.iconGroupId").type(NUMBER).description("아이콘 그룹 iconGroupId"),
                                        fieldWithPath("iconGroupDetail.iconGroupSummaryInfo.title").type(STRING).description("아이콘 그룹 제목"),
                                        fieldWithPath("iconGroupDetail.iconGroupSummaryInfo.creatorNickname").type(STRING).description("아이콘 제작자 닉네임"),
                                        fieldWithPath("iconGroupDetail.iconGroupSummaryInfo.thumbnailImageUrl").type(STRING).description("아이콘 그룹 thumbnailImage url"),
                                        fieldWithPath("iconGroupDetail.iconGroupSummaryInfo.description").type(STRING).description("아이콘 그룹 설명"),
                                        fieldWithPath("iconGroupDetail.iconGroupSummaryInfo.iconType").type(STRING).description("아이콘 그룹 타입"),
                                        fieldWithPath("iconGroupDetail.iconGroupSummaryInfo.iconState").type(STRING).description("아이콘 그룹 상태"),
                                        fieldWithPath("iconGroupDetail.iconGroupSummaryInfo.price").type(NUMBER).description("아이콘 그룹 가격"),
                                        fieldWithPath("iconGroupDetail.icons[].iconId").type(NUMBER).description("아이콘 Id"),
                                        fieldWithPath("iconGroupDetail.icons[].iconImageUrl").type(STRING).description("아이콘 image url"),
                                        fieldWithPath("isBuy").type(BOOLEAN).description("구매 여부")
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
                                .tag("[앱] 아이콘")
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
