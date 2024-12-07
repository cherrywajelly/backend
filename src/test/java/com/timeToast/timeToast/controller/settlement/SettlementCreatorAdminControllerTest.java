package com.timeToast.timeToast.controller.settlement;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.service.settlement.SettlementService;
import com.timeToast.timeToast.service.settlement.SettlementServiceTest;
import com.timeToast.timeToast.util.BaseControllerTests;
import com.timeToast.timeToast.util.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

public class SettlementCreatorAdminControllerTest extends BaseControllerTests {

    private final SettlementService settlementService = new SettlementServiceTest();

    @Override
    protected Object initController() {
        return new SettlementCreatorController(settlementService);
    }

    @DisplayName("제작자는 자신의 정산 목록을 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void approvalSettlement() throws Exception {


        mockMvc.perform(
                        get("/api/v2/settlements")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)

                )
                .andExpect(status().isOk())
                .andDo(document("제작자 정산 목록 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("제작자 - 정산")
                                .summary("제작자는 자신의 정산 목록을 조회할 수 있다.")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("settlementCreatorInfoResponses[0].year").type(NUMBER).description("year"),
                                        fieldWithPath("settlementCreatorInfoResponses[0].month").type(NUMBER).description("month"),
                                        fieldWithPath("settlementCreatorInfoResponses[0].settlementDate").type(ARRAY).description("승인 날짜")
                                )
                                .build()
                        )));
    }



    @DisplayName("제작자는 정산 상세 조회를 할 수 있다.")
    @WithMockCustomUser
    @Test
    void getMonthSettlement() throws Exception {


        mockMvc.perform(
                        get("/api/v2/settlements/detail")
                                .param("year", "1")
                                .param("month", "1")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)

                )
                .andExpect(status().isOk())
                .andDo(document("제작자 정산 상세 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("제작자 - 정산")
                                .summary("제작자는 정산 상세 조회할 수 있다.")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .queryParameters(
                                        parameterWithName("year").description("year"),
                                        parameterWithName("month").description("month")
                                )
                                .responseFields(
                                        fieldWithPath("year").type(NUMBER).description("year"),
                                        fieldWithPath("month").type(NUMBER).description("month"),
                                        fieldWithPath("creatorNickname").type(STRING).description("제작자 닉네임"),
                                        fieldWithPath("salesIconCount").type(NUMBER).description("판매한 아이콘 수"),
                                        fieldWithPath("totalRevenue").type(NUMBER).description("전체 수익"),
                                        fieldWithPath("settlement").type(NUMBER).description("정산금"),
                                        fieldWithPath("bank").type(STRING).description("은행"),
                                        fieldWithPath("accountNumber").type(STRING).description("계좌번호"),
                                        fieldWithPath("settlementState").type(STRING).description("정산 승인 상태"),
                                        fieldWithPath("settlementIcons[0].title").type(STRING).description("아이콘 제목"),
                                        fieldWithPath("settlementIcons[0].revenue").type(NUMBER).description("아이콘 별 수익"),
                                        fieldWithPath("settlementIcons[0].salesCount").type(NUMBER).description("아이콘 별 판매 수량"),
                                        fieldWithPath("settlementIcons[0].settlementState").type(STRING).description("아이콘 별 정산 승인 상태")
                                        )
                                .build()
                        )));
    }


}
