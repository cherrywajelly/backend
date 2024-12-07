package com.timeToast.timeToast.controller.payment;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.service.payment.PaymentService;
import com.timeToast.timeToast.service.payment.PaymentServiceTest;
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
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PaymentAdminControllerTest extends BaseControllerTests {

    private final PaymentService paymentService = new PaymentServiceTest();
    @Override
    protected Object initController() {
        return new PaymentAdminController(paymentService);
    }

    @DisplayName("모든 결제 정보 조회")
    @WithMockCustomUser
    @Test
    void getPayments() throws Exception {


        mockMvc.perform(
                        get("/api/v3/payments")
                                .param("page", "1")
                                .param("size", "20")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)

                )
                .andExpect(status().isOk())
                .andDo(document("결제 정보 목록 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("결제")
                                .summary("관리자 결제 정보 목록 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .queryParameters(
                                        parameterWithName("page").description("page 번호"),
                                        parameterWithName("size").description("page 크기")

                                )
                                .responseFields(
                                        fieldWithPath("paymentsAdminResponses[0].paymentId").type(NUMBER).description("결제 id"),
                                        fieldWithPath("paymentsAdminResponses[0].nickname").type(STRING).description("닉네임"),
                                        fieldWithPath("paymentsAdminResponses[0].itemName").type(STRING).description("아이템 이름"),
                                        fieldWithPath("paymentsAdminResponses[0].itemType").type(STRING).description("아이템 종류"),
                                        fieldWithPath("paymentsAdminResponses[0].createdAt").type(STRING).description("결제 생성 일자"),
                                        fieldWithPath("paymentsAdminResponses[0].amount").type(NUMBER).description("결제 금액"),
                                        fieldWithPath("paymentsAdminResponses[0].paymentState").type(STRING).description("결제 상태"),
                                        fieldWithPath("paymentsAdminResponses[0].expiredDate").type(STRING).description("만료일")
                                        )
                                .build()
                        )));
    }

    @DisplayName("결제 상세 조회")
    @WithMockCustomUser
    @Test
    void getPaymentDetails() throws Exception {


        mockMvc.perform(
                        get("/api/v3/payments/{paymentId}", 1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)

                )
                .andExpect(status().isOk())
                .andDo(document("결제 정보 상세 조회",
                        pathParameters(
                                parameterWithName("paymentId").description("결제 id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("결제")
                                .summary("관리자 결제 정보 상세 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("orderId").type(STRING).description("주문 id"),
                                        fieldWithPath("nickname").type(STRING).description("주문자 nickname"),
                                        fieldWithPath("itemType").type(STRING).description("아이템 종류"),
                                        fieldWithPath("itemName").type(STRING).description("아이템 이름"),
                                        fieldWithPath("amount").type(NUMBER).description("결제 금액"),
                                        fieldWithPath("paymentState").type(STRING).description("결제 상태"),
                                        fieldWithPath("createdAt").type(STRING).description("결제 생성일"),
                                        fieldWithPath("expiredDate").type(STRING).description("만료일"),
                                        fieldWithPath("iconThumbnailImageUrl").type(STRING).description("아이콘 썸네일 이미지 url")
                                )
                                .build()
                        )));
    }



}
