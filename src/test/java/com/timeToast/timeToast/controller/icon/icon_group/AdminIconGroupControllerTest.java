package com.timeToast.timeToast.controller.icon.icon_group;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.controller.icon.AdminIconGroupController;
import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.dto.icon.request.IconGroupStateRequest;
import com.timeToast.timeToast.service.icon.AdminIconService;
import com.timeToast.timeToast.service.icon.AdminIconServiceTest;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminIconGroupControllerTest extends BaseControllerTests {
    private final AdminIconService adminIconService = new AdminIconServiceTest();


    @Override
    protected Object initController() {
        return new AdminIconGroupController(adminIconService);
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
                .andDo(document("관리자는 아이콘을 승인할 수 있다.",
                        resource(ResourceSnippetParameters.builder()
                                .tag("[어드민] 아이콘")
                                .summary("관리자의 아이콘 승인")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("iconGroupId").type(NUMBER).description("아이콘 그룹 Id"),
                                        fieldWithPath("title").type(STRING).description("아이콘 그룹 제목"),
                                        fieldWithPath("creatorNickname").type(STRING).description("아이콘 제작자 닉네임"),
                                        fieldWithPath("thumbnailImageUrl").type(STRING).description("아이콘 그룹 thumbnailImage url"),
                                        fieldWithPath("description").type(STRING).description("아이콘 그룹 설명"),
                                        fieldWithPath("iconType").type(STRING).description("아이콘 그룹 타입"),
                                        fieldWithPath("iconState").type(STRING).description("아이콘 그룹 state"),
                                        fieldWithPath("price").type(NUMBER).description("아이콘 그룹 가격")
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
                                parameterWithName("iconGroupId").description("아이콘 그룹 Id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("[어드민] 아이콘")
                                .summary("관리자의 아이콘 그룹 단일 상세 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("iconGroupInfo.iconGroupId").type(NUMBER).description("아이콘 그룹 Id"),
                                        fieldWithPath("iconGroupInfo.title").type(STRING).description("아이콘 그룹 제목"),
                                        fieldWithPath("iconGroupInfo.creatorNickname").type(STRING).description("아이콘 제작자 닉네임"),
                                        fieldWithPath("iconGroupInfo.thumbnailImageUrl").type(STRING).description("아이콘 그룹 thumbnailImage url"),
                                        fieldWithPath("iconGroupInfo.description").type(STRING).description("아이콘 그룹 설명"),
                                        fieldWithPath("iconGroupInfo.iconType").type(STRING).description("아이콘 그룹 타입"),
                                        fieldWithPath("iconGroupInfo.iconState").type(STRING).description("아이콘 그룹 state"),
                                        fieldWithPath("iconGroupInfo.price").type(NUMBER).description("아이콘 그룹 가격"),
                                        fieldWithPath("icons[].iconId").type(NUMBER).description("아이콘 Id"),
                                        fieldWithPath("icons[].iconImageUrl").type(STRING).description("아이콘 이미지 url")
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
                                .tag("[어드민] 아이콘")
                                .summary("관리자의 아이콘 그룹 전체 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("iconGroupInfos[].iconGroupId").type(NUMBER).description("아이콘 그룹 Id"),
                                        fieldWithPath("iconGroupInfos[].title").type(STRING).description("아이콘 그룹 제목"),
                                        fieldWithPath("iconGroupInfos[].creatorNickname").type(STRING).description("아이콘 제작자 닉네임"),
                                        fieldWithPath("iconGroupInfos[].thumbnailImageUrl").type(STRING).description("아이콘 그룹 thumbnailImage url"),
                                        fieldWithPath("iconGroupInfos[].description").type(STRING).description("아이콘 그룹 설명"),
                                        fieldWithPath("iconGroupInfos[].iconType").type(STRING).description("아이콘 그룹 타입"),
                                        fieldWithPath("iconGroupInfos[].iconState").type(STRING).description("아이콘 그룹 state"),
                                        fieldWithPath("iconGroupInfos[].price").type(NUMBER).description("아이콘 그룹 가격")
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
                                .tag("[어드민] 아이콘")
                                .summary("관리자 아이콘 그룹 승인 리스트 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("iconGroupInfos[].iconGroupId").type(NUMBER).description("아이콘 그룹 Id"),
                                        fieldWithPath("iconGroupInfos[].title").type(STRING).description("아이콘 그룹 제목"),
                                        fieldWithPath("iconGroupInfos[].creatorNickname").type(STRING).description("아이콘 제작자 닉네임"),
                                        fieldWithPath("iconGroupInfos[].thumbnailImageUrl").type(STRING).description("아이콘 그룹 thumbnailImage url"),
                                        fieldWithPath("iconGroupInfos[].description").type(STRING).description("아이콘 그룹 설명"),
                                        fieldWithPath("iconGroupInfos[].iconType").type(STRING).description("아이콘 그룹 타입"),
                                        fieldWithPath("iconGroupInfos[].iconState").type(STRING).description("아이콘 그룹 state"),
                                        fieldWithPath("iconGroupInfos[].price").type(NUMBER).description("아이콘 그룹 가격")
                                )
                                .build()
                        )));
    }

    @DisplayName("관리자는 사용자가 소유한 아이콘의 정보를 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getIconGroups() throws Exception {

        mockMvc.perform(
                        get("/api/v3/iconGroups/members/{memberId}", 1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("관리자 사용자 소유 아이콘 조회",
                        pathParameters(
                                parameterWithName("memberId").description("사용자 Id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("[어드민] 아이콘")
                                .summary("관리자 아이콘 정보 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("iconGroupDetails[].iconGroupInfo.iconGroupId").type(NUMBER).description("아이콘 그룹 Id"),
                                        fieldWithPath("iconGroupDetails[].iconGroupInfo.title").type(STRING).description("아이콘 그룹 제목"),
                                        fieldWithPath("iconGroupDetails[].iconGroupInfo.creatorNickname").type(STRING).description("아이콘 제작자 닉네임"),
                                        fieldWithPath("iconGroupDetails[].iconGroupInfo.thumbnailImageUrl").type(STRING).description("아이콘 그룹 thumbnailImage url"),
                                        fieldWithPath("iconGroupDetails[].iconGroupInfo.description").type(STRING).description("아이콘 그룹 설명"),
                                        fieldWithPath("iconGroupDetails[].iconGroupInfo.iconType").type(STRING).description("아이콘 그룹 타입"),
                                        fieldWithPath("iconGroupDetails[].iconGroupInfo.iconState").type(STRING).description("아이콘 그룹 state"),
                                        fieldWithPath("iconGroupDetails[].iconGroupInfo.price").type(NUMBER).description("아이콘 그룹 가격"),
                                        fieldWithPath("iconGroupDetails[].icons[0].iconId").type(NUMBER).description("아이콘 Id"),
                                        fieldWithPath("iconGroupDetails[].icons[0].iconImageUrl").type(STRING).description("아이콘 이미지 url")
                                )
                                .build()
                        )));
    }

    @DisplayName("관리자의 제작자의 모든 아이콘 그룹 조회")
    @WithMockCustomUser
    @Test
    void getIconGroupsByCreator() throws Exception {
        mockMvc.perform(
                        get("/api/v3/iconGroups/creators/{creatorId}",1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("관리자의 제작자의 전체 아이콘 상세 조회",
                        pathParameters(
                                parameterWithName("creatorId").description("제작자 Id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("[어드민] 아이콘")
                                .summary("관리자는 제작자의 전체 아이콘을 조회할 수 있다.")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("creatorIconGroups[].iconGroupInfo.iconGroupId").type(NUMBER).description("아이콘 그룹 id"),
                                        fieldWithPath("creatorIconGroups[].iconGroupInfo.title").type(STRING).description("아이콘 그룹 제목"),
                                        fieldWithPath("creatorIconGroups[].iconGroupInfo.creatorNickname").type(STRING).description("제작자 닉네임"),
                                        fieldWithPath("creatorIconGroups[].iconGroupInfo.thumbnailImageUrl").type(STRING).description("아이콘 그룹 thumbnail url"),
                                        fieldWithPath("creatorIconGroups[].iconGroupInfo.description").type(STRING).description("아이콘 그룹 설명"),
                                        fieldWithPath("creatorIconGroups[].iconGroupInfo.iconType").type(STRING).description("아이콘 그룹 타입"),
                                        fieldWithPath("creatorIconGroups[].iconGroupInfo.iconState").type(STRING).description("아이콘 그룹 상태"),
                                        fieldWithPath("creatorIconGroups[].iconGroupInfo.price").type(NUMBER).description("가격"),
                                        fieldWithPath("creatorIconGroups[].icons[].iconId").type(NUMBER).description("아이콘 id"),
                                        fieldWithPath("creatorIconGroups[].icons[].iconImageUrl").type(STRING).description("아이콘 image url"),
                                        fieldWithPath("creatorIconGroups[].iconGroupOrderInfo.orderCount").type(NUMBER).description("아이콘 별 판매 수"),
                                        fieldWithPath("creatorIconGroups[].iconGroupOrderInfo.income").type(NUMBER).description("아이콘 별 판매 수익"),
                                        fieldWithPath("totalIconCount").type(NUMBER).description("총 아이콘 수"),
                                        fieldWithPath("totalOrderCount").type(NUMBER).description("총 주문 수"),
                                        fieldWithPath("totalIncome").type(NUMBER).description("총 수익"),
                                        fieldWithPath("totalSettlement").type(NUMBER).description("총 정산액")
                                )
                                .build()
                        )));
    }

}
