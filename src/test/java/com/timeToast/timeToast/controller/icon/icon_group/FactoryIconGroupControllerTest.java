package com.timeToast.timeToast.controller.icon.icon_group;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.controller.icon.FactoryIconGroupController;
import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.dto.icon.request.IconGroupPostRequest;
import com.timeToast.timeToast.service.icon.AdminIconService;
import com.timeToast.timeToast.service.icon.AdminIconServiceTest;
import com.timeToast.timeToast.util.BaseControllerTests;
import com.timeToast.timeToast.util.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.timeToast.timeToast.util.TestConstant.TEST_ACCESS_TOKEN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FactoryIconGroupControllerTest extends BaseControllerTests {
    private final AdminIconService adminIconService = new AdminIconServiceTest();

    @Override
    protected Object initController() {
        return new FactoryIconGroupController(adminIconService);
    }


    @DisplayName("새로운 아이콘 그룹을 등록할 수 있다.")
    @Test
    void postIconGroup() throws Exception {
        IconGroupPostRequest iconGroupPostRequest = new IconGroupPostRequest("name", 1500, IconType.JAM, "description");
        String json = objectMapper.writeValueAsString(iconGroupPostRequest);

        mockMvc.perform(
                        multipart("/api/v2/iconGroups")
                                .file("thumbnailIcon", "thumbnailIcon.png".getBytes())
                                .file("files", "files.png".getBytes())
                                .file(new MockMultipartFile("iconGroupPostRequest", "iconGroupPostRequest", MediaType.APPLICATION_JSON_VALUE, json.getBytes()))
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(status().isOk())
                .andDo(document("아이콘 등록",
                        requestParts(
                                partWithName("thumbnailIcon").description("아이콘 대표 이미지"),
                                partWithName("files").description("아이콘 이미지"),
                                partWithName("iconGroupPostRequest").description("아이콘 요청 정보")
                        ),
                        requestPartBody("iconGroupPostRequest"), // JSON 필드 문서화
                        requestPartFields("iconGroupPostRequest",
                                fieldWithPath("name").type(STRING).description("아이콘 제목"),
                                fieldWithPath("price").type(NUMBER).description("아이콘 가격"),
                                fieldWithPath("iconType").type(STRING).description("아이콘 종류 (JAM | TOAST)"),
                                fieldWithPath("description").type(STRING).description("아이콘 설명")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("[팩토리] 아이콘")
                                .summary("새로운 아이콘 등록")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("iconGroupId").type(NUMBER).description("아이콘 그룹 Id"),
                                        fieldWithPath("title").type(STRING).description("아이콘 그룹 제목"),
                                        fieldWithPath("creatorNickname").type(STRING).description("아이콘 제작자 닉네임"),
                                        fieldWithPath("thumbnailImageUrl").type(STRING).description("아이콘 그룹 thumbnailImageUrl"),
                                        fieldWithPath("description").type(STRING).description("아이콘 그룹 설명"),
                                        fieldWithPath("iconType").type(STRING).description("아이콘 그룹 타입"),
                                        fieldWithPath("iconState").type(STRING).description("아이콘 그룹 state"),
                                        fieldWithPath("price").type(NUMBER).description("아이콘 그룹 가격")
                                )
                                .build()
                        )));
    }


    @DisplayName("제작자는 자신의 아이콘 그룹 목록을 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getIconGroup() throws Exception {

        mockMvc.perform(
                        get("/api/v2/iconGroups")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("제작자의 아이콘 목록 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("[팩토리] 아이콘")
                                .summary("제작자의 아이콘 목록 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("creatorIconGroups[].iconGroupInfo.iconGroupId").type(NUMBER).description("아이콘 그룹 Id"),
                                        fieldWithPath("creatorIconGroups[].iconGroupInfo.title").type(STRING).description("아이콘 그룹 제목"),
                                        fieldWithPath("creatorIconGroups[].iconGroupInfo.creatorNickname").type(STRING).description("아이콘 제작자 닉네임"),
                                        fieldWithPath("creatorIconGroups[].iconGroupInfo.thumbnailImageUrl").type(STRING).description("아이콘 그룹 thumbnailImageUrl"),
                                        fieldWithPath("creatorIconGroups[].iconGroupInfo.description").type(STRING).description("아이콘 그룹 설명"),
                                        fieldWithPath("creatorIconGroups[].iconGroupInfo.iconType").type(STRING).description("아이콘 그룹 타입"),
                                        fieldWithPath("creatorIconGroups[].iconGroupInfo.iconState").type(STRING).description("아이콘 그룹 상태"),
                                        fieldWithPath("creatorIconGroups[].iconGroupInfo.price").type(NUMBER).description("아이콘 그룹 가격"),
                                        fieldWithPath("creatorIconGroups[].icons[].iconId").type(NUMBER).description("아이콘 id"),
                                        fieldWithPath("creatorIconGroups[].icons[].iconImageUrl").type(STRING).description("아이콘 imageUrl"),
                                        fieldWithPath("creatorIconGroups[].iconGroupOrderInfo.orderCount").type(NUMBER).description("아이콘 별 주문 갯수"),
                                        fieldWithPath("creatorIconGroups[].iconGroupOrderInfo.income").type(NUMBER).description("아이콘 별 판매 수익"),
                                        fieldWithPath("totalIconCount").type(NUMBER).description("총 아이콘 갯수"),
                                        fieldWithPath("totalOrderCount").type(NUMBER).description("아이콘 총 판매 갯수"),
                                        fieldWithPath("totalIncome").type(NUMBER).description("아이콘 총 수익"),
                                        fieldWithPath("totalSettlement").type(NUMBER).description("아이콘 총 정산액")

                                )
                                .build()
                        )));
    }

    @DisplayName("제작자의 아이콘 그룹 단일 상세 정보를 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getIconGroupDetail() throws Exception {

        mockMvc.perform(
                        get("/api/v2/iconGroups/{iconGroupId}", 1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("제작자의 아이콘 그룹 단일 상세 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("[팩토리] 아이콘")
                                .summary("제작자의 아이콘 그룹 단일 상세 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .pathParameters(
                                        parameterWithName("iconGroupId").description("아이콘 그룹 Id")
                                )
                                .responseFields(
                                        fieldWithPath("iconGroupInfo.iconGroupId").type(NUMBER).description("아이콘 그룹 Id"),
                                        fieldWithPath("iconGroupInfo.title").type(STRING).description("아이콘 그룹 제목"),
                                        fieldWithPath("iconGroupInfo.creatorNickname").type(STRING).description("아이콘 제작자 닉네임"),
                                        fieldWithPath("iconGroupInfo.thumbnailImageUrl").type(STRING).description("아이콘 그룹 thumbnailImageUrl"),
                                        fieldWithPath("iconGroupInfo.description").type(STRING).description("아이콘 그룹 설명"),
                                        fieldWithPath("iconGroupInfo.iconType").type(STRING).description("아이콘 그룹 타입"),
                                        fieldWithPath("iconGroupInfo.iconState").type(STRING).description("아이콘 그룹 상태"),
                                        fieldWithPath("iconGroupInfo.price").type(NUMBER).description("아이콘 그룹 가격"),
                                        fieldWithPath("icons[].iconId").type(NUMBER).description("아이콘 id"),
                                        fieldWithPath("icons[].iconImageUrl").type(STRING).description("아이콘 imageUrl"),
                                        fieldWithPath("iconGroupOrderInfo.orderCount").type(NUMBER).description("아이콘 별 주문 갯수"),
                                        fieldWithPath("iconGroupOrderInfo.income").type(NUMBER).description("아이콘 별 판매 수익")
                                )
                                .build()
                        )));
    }

}