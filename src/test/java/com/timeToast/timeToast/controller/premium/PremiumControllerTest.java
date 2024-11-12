package com.timeToast.timeToast.controller.premium;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.service.premium.PremiumService;
import com.timeToast.timeToast.service.premium.PremiumServiceTest;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PremiumControllerTest extends BaseControllerTests {

    private final PremiumService premiumService = new PremiumServiceTest();
    @Override
    protected Object initController() {
        return new PremiumController(premiumService);
    }

    @DisplayName("로그인한 사용자의 프리미엄 정보를 저장할 수 있다.")
    @WithMockCustomUser
    @Test
    void savePremium() throws Exception {

        mockMvc.perform(
                        post("/api/v1/premiums/{premiumId}", 1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("로그인한 사용자의 프리미엄 정보 저장",
                        pathParameters(
                                parameterWithName("premiumId").description("premium id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("프리미엄")
                                .summary("로그인한 사용자의 프리미엄 정보 저장")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("premiumId").type(NUMBER).description("premium id"),
                                        fieldWithPath("premiumType").type(STRING).description("premium type"),
                                        fieldWithPath("price").type(NUMBER).description("가격"),
                                        fieldWithPath("count").type(NUMBER).description("이미지 갯수"),
                                        fieldWithPath("description").type(STRING).description("설명")
                                )
                                .build()
                        )));
    }

    @DisplayName("프리미엄 정보를 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getPremiums() throws Exception {

        mockMvc.perform(
                        get("/api/v1/premiums")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("프리미엄 정보 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("프리미엄")
                                .summary("프리미엄 정보 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("premiumResponses[0].premiumId").type(NUMBER).description("premium id"),
                                        fieldWithPath("premiumResponses[0].premiumType").type(STRING).description("premium type"),
                                        fieldWithPath("premiumResponses[0].price").type(NUMBER).description("가격"),
                                        fieldWithPath("premiumResponses[0].count").type(NUMBER).description("이미지 갯수"),
                                        fieldWithPath("premiumResponses[0].description").type(STRING).description("설명")
                                )
                                .build()
                        )));
    }
}
