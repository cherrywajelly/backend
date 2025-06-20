package com.timeToast.timeToast.controller.icon.icon_group;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.controller.icon.AppIconGroupController;
import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.service.icon.IconService;
import com.timeToast.timeToast.service.icon.IconServiceTest;
import com.timeToast.timeToast.util.BaseControllerTests;
import com.timeToast.timeToast.util.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

public class AppIconGroupControllerTest extends BaseControllerTests {
    private final IconService iconService = new IconServiceTest();

    @Override
    protected Object initController() {
        return new AppIconGroupController(iconService);
    }

    @DisplayName("사용자의 아이콘 그룹 목록을 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getUserIconGroups() throws Exception {

        mockMvc.perform(
                        get("/api/v1/iconGroups")
                                .param("iconType", IconType.TOAST.toString())
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("사용자의 아이콘 그룹 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("[앱] 아이콘")
                                .summary("사용자의 아이콘 그룹 목록 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .queryParameters(
                                        parameterWithName("iconType").description("아이콘 그룹 타입(TOAST|JAM)")
                                )
                                .responseFields(
                                        fieldWithPath("userIconGroupResponses[].iconGroupDetail.iconGroupInfo.iconGroupId").type(NUMBER).description("아이콘 그룹 Id"),
                                        fieldWithPath("userIconGroupResponses[].iconGroupDetail.iconGroupInfo.title").type(STRING).description("아이콘 그룹 제목"),
                                        fieldWithPath("userIconGroupResponses[].iconGroupDetail.iconGroupInfo.creatorNickname").type(STRING).description("아이콘 제작자 닉네임"),
                                        fieldWithPath("userIconGroupResponses[].iconGroupDetail.iconGroupInfo.thumbnailImageUrl").type(STRING).description("아이콘 그룹 thumbnailImage url"),
                                        fieldWithPath("userIconGroupResponses[].iconGroupDetail.iconGroupInfo.description").type(STRING).description("아이콘 그룹 설명"),
                                        fieldWithPath("userIconGroupResponses[].iconGroupDetail.iconGroupInfo.iconType").type(STRING).description("아이콘 그룹 타입"),
                                        fieldWithPath("userIconGroupResponses[].iconGroupDetail.iconGroupInfo.iconState").type(STRING).description("아이콘 그룹 상태"),
                                        fieldWithPath("userIconGroupResponses[].iconGroupDetail.iconGroupInfo.price").type(NUMBER).description("아이콘 그룹 가격"),
                                        fieldWithPath("userIconGroupResponses[].iconGroupDetail.icons[].iconId").type(NUMBER).description("아이콘 Id"),
                                        fieldWithPath("userIconGroupResponses[].iconGroupDetail.icons[].iconImageUrl").type(STRING).description("아이콘 image url"),
                                        fieldWithPath("userIconGroupResponses[].isBuy").type(BOOLEAN).description("구매 여부")
                                )
                                .build()
                        )));
    }


    @DisplayName("마켓의 아이콘 그룹 목록을 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getMarketIconGroups() throws Exception {

        mockMvc.perform(
                        get("/api/v1/iconGroups/market")
                                .param("iconType", IconType.TOAST.toString())
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("마켓의 아이콘 그룹 목록 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("[앱] 아이콘")
                                .summary("마켓의 아이콘 그룹 목록 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .queryParameters(
                                        parameterWithName("iconType").description("아이콘 그룹 타입(TOAST|JAM)")
                                )
                                .responseFields(
                                        fieldWithPath("marketIconGroupResponses[].iconGroupInfo.iconGroupId").type(NUMBER).description("아이콘 그룹 Id"),
                                        fieldWithPath("marketIconGroupResponses[].iconGroupInfo.title").type(STRING).description("아이콘 그룹 제목"),
                                        fieldWithPath("marketIconGroupResponses[].iconGroupInfo.creatorNickname").type(STRING).description("아이콘 제작자 닉네임"),
                                        fieldWithPath("marketIconGroupResponses[].iconGroupInfo.thumbnailImageUrl").type(STRING).description("아이콘 그룹 thumbnailImage url"),
                                        fieldWithPath("marketIconGroupResponses[].iconGroupInfo.description").type(STRING).description("아이콘 그룹 설명"),
                                        fieldWithPath("marketIconGroupResponses[].iconGroupInfo.iconType").type(STRING).description("아이콘 그룹 타입"),
                                        fieldWithPath("marketIconGroupResponses[].iconGroupInfo.iconState").type(STRING).description("아이콘 그룹 상태"),
                                        fieldWithPath("marketIconGroupResponses[].iconGroupInfo.price").type(NUMBER).description("아이콘 그룹 가격"),
                                        fieldWithPath("marketIconGroupResponses[].isBuy").type(BOOLEAN).description("구매 여부")

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
                                parameterWithName("iconGroupId").description("아이콘 그룹 Id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("[앱] 아이콘")
                                .summary("아이콘 그룹 단일 상세 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("iconGroupDetail.iconGroupInfo.iconGroupId").type(NUMBER).description("아이콘 그룹 Id"),
                                        fieldWithPath("iconGroupDetail.iconGroupInfo.title").type(STRING).description("아이콘 그룹 제목"),
                                        fieldWithPath("iconGroupDetail.iconGroupInfo.creatorNickname").type(STRING).description("아이콘 제작자 닉네임"),
                                        fieldWithPath("iconGroupDetail.iconGroupInfo.thumbnailImageUrl").type(STRING).description("아이콘 그룹 thumbnailImage url"),
                                        fieldWithPath("iconGroupDetail.iconGroupInfo.description").type(STRING).description("아이콘 그룹 설명"),
                                        fieldWithPath("iconGroupDetail.iconGroupInfo.iconType").type(STRING).description("아이콘 그룹 타입"),
                                        fieldWithPath("iconGroupDetail.iconGroupInfo.iconState").type(STRING).description("아이콘 그룹 상태"),
                                        fieldWithPath("iconGroupDetail.iconGroupInfo.price").type(NUMBER).description("아이콘 그룹 가격"),
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
