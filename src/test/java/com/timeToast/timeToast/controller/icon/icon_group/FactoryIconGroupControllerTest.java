package com.timeToast.timeToast.controller.icon.icon_group;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.controller.icon.FactoryIconGroupController;
import com.timeToast.timeToast.domain.enums.icon_group.IconBuiltin;
import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupPostRequest;
import com.timeToast.timeToast.service.icon.AdminIconService;
import com.timeToast.timeToast.service.icon.icon_group.AdminIconServiceTest;
import com.timeToast.timeToast.util.BaseControllerTests;
import com.timeToast.timeToast.util.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;

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


    @DisplayName("새로운 아이콘을 등록할 수 있다.")
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
                                .tag("[팩토리] 아이콘")
                                .summary("아이콘 목록 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("iconGroupOverviews[].iconGroupSummaryInfo.iconGroupId").type(NUMBER).description("아이콘 그룹 Id"),
                                        fieldWithPath("iconGroupOverviews[].iconGroupSummaryInfo.title").type(STRING).description("아이콘 그룹 제목"),
                                        fieldWithPath("iconGroupOverviews[].iconGroupSummaryInfo.creatorNickname").type(STRING).description("아이콘 제작자 닉네임"),
                                        fieldWithPath("iconGroupOverviews[].iconGroupSummaryInfo.thumbnailImageUrl").type(STRING).description("아이콘 그룹 thumbnailImageUrl"),
                                        fieldWithPath("iconGroupOverviews[].iconGroupSummaryInfo.description").type(STRING).description("아이콘 그룹 설명"),
                                        fieldWithPath("iconGroupOverviews[].iconGroupSummaryInfo.iconType").type(STRING).description("아이콘 그룹 타입"),
                                        fieldWithPath("iconGroupOverviews[].iconGroupSummaryInfo.price").type(NUMBER).description("아이콘 그룹 가격"),
                                        fieldWithPath("iconGroupOverviews[].iconState").type(STRING).description("아이콘 그룹 상태"),
                                        fieldWithPath("iconGroupOverviews[].icons[].iconId").type(NUMBER).description("아이콘 id"),
                                        fieldWithPath("iconGroupOverviews[].icons[].iconImageUrl").type(STRING).description("아이콘 imageUrl"),
                                        fieldWithPath("iconGroupOverviews[].orderCount").type(NUMBER).description("아이콘 별 주문 갯수"),
                                        fieldWithPath("iconGroupOverviews[].income").type(NUMBER).description("아이콘 별 판매 수익"),
                                        fieldWithPath("totalIconCount").type(NUMBER).description("총 아이콘 갯수"),
                                        fieldWithPath("totalOrderCount").type(NUMBER).description("아이콘 총 판매 갯수"),
                                        fieldWithPath("totalIncome").type(NUMBER).description("아이콘 총 수익"),
                                        fieldWithPath("totalSettlement").type(NUMBER).description("아이콘 총 정산액")

                                )
                                .build()
                        )));
    }

    @DisplayName("제작자가 제작한 아이콘 그룹의 상세 정보를 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getIconGroupDetail() throws Exception {

        mockMvc.perform(
                        get("/api/v2/iconGroups/{iconGroupId}", 1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("아이콘 그룹 상세 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("[팩토리] 아이콘")
                                .summary("아이콘 그룹 상세 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .pathParameters(
                                        parameterWithName("iconGroupId").description("아이콘 그룹 Id")
                                )
                                .responseFields(
                                        fieldWithPath("iconGroupSummaryInfo.iconGroupId").type(NUMBER).description("아이콘 그룹 Id"),
                                        fieldWithPath("iconGroupSummaryInfo.title").type(STRING).description("아이콘 그룹 제목"),
                                        fieldWithPath("iconGroupSummaryInfo.creatorNickname").type(STRING).description("아이콘 제작자 닉네임"),
                                        fieldWithPath("iconGroupSummaryInfo.thumbnailImageUrl").type(STRING).description("아이콘 그룹 thumbnailImageUrl"),
                                        fieldWithPath("iconGroupSummaryInfo.description").type(STRING).description("아이콘 그룹 설명"),
                                        fieldWithPath("iconGroupSummaryInfo.iconType").type(STRING).description("아이콘 그룹 타입"),
                                        fieldWithPath("iconGroupSummaryInfo.price").type(NUMBER).description("아이콘 그룹 가격"),
                                        fieldWithPath("iconState").type(STRING).description("아이콘 그룹 상태"),
                                        fieldWithPath("icons[].iconId").type(NUMBER).description("아이콘 id"),
                                        fieldWithPath("icons[].iconImageUrl").type(STRING).description("아이콘 imageUrl"),
                                        fieldWithPath("orderCount").type(NUMBER).description("아이콘 별 주문 갯수"),
                                        fieldWithPath("income").type(NUMBER).description("아이콘 별 판매 수익")
                                )
                                .build()
                        )));
    }

}