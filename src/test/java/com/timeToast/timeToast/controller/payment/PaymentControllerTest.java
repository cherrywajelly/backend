package com.timeToast.timeToast.controller.payment;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.dto.payment.PaymentSaveRequest;
import com.timeToast.timeToast.dto.payment.PaymentSuccessRequest;
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
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PaymentControllerTest extends BaseControllerTests {

    private final PaymentService paymentService = new PaymentServiceTest();
    @Override
    protected Object initController() {
        return new PaymentController(paymentService);
    }

    @DisplayName("사용자는 결제 정보를 저장할 수 있다.")
    @WithMockCustomUser
    @Test
    void postPayment() throws Exception {

        PaymentSaveRequest paymentSaveRequest = new PaymentSaveRequest(1L, 100, ItemType.ICON, "success", "fail");
        String json = objectMapper.writeValueAsString(paymentSaveRequest);

        mockMvc.perform(
                        post("/api/v1/payments")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                                .contentType(APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("결제 정보 저장",
                        resource(ResourceSnippetParameters.builder()
                                .tag("결제")
                                .summary("사용자 결제 정보 저장하기")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .requestFields(
                                        fieldWithPath("itemId").type(NUMBER).description("아이콘 그룹 id/premium id"),
                                        fieldWithPath("amount").type(NUMBER).description("가격"),
                                        fieldWithPath("itemType").type(STRING).description("아이템 종류 ICON/PREMIUM"),
                                        fieldWithPath("successUrl").type(STRING).description("successUrl"),
                                        fieldWithPath("failUrl").type(STRING).description("failUrl")

                                        )
                                .responseFields(
                                        fieldWithPath("paymentId").type(NUMBER).description("결제 id"),
                                        fieldWithPath("orderId").type(STRING).description("order id"),
                                        fieldWithPath("orderName").type(STRING).description("주문 제목"),
                                        fieldWithPath("successUrl").type(STRING).description("successUrl"),
                                        fieldWithPath("failUrl").type(STRING).description("failUrl"),
                                        fieldWithPath("customerEmail").type(STRING).description("사용자 이메일")
                                        )
                                .build()
                        )));
    }

    @DisplayName("사용자는 성공한 결제에 대해 승인을 요청할 수 있다.")
    @WithMockCustomUser
    @Test
    void successPayment() throws Exception {

        PaymentSuccessRequest paymentSuccessRequest = new PaymentSuccessRequest("paymentKey", "orderId", 100);
        String json = objectMapper.writeValueAsString(paymentSuccessRequest);

        mockMvc.perform(
                        post("/api/v1/payments/{paymentId}/success",1)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                                .contentType(APPLICATION_JSON)
                                .content(json)

                )
                .andExpect(status().isOk())
                .andDo(document("결제 성공 승인 요청",
                        pathParameters(
                                parameterWithName("paymentId").description("paymentId")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("결제")
                                .summary("결제 성공 승인 요청하기")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .requestFields(
                                        fieldWithPath("paymentKey").type(STRING).description("paymentKey"),
                                        fieldWithPath("orderId").type(STRING).description("orderId"),
                                        fieldWithPath("amount").type(NUMBER).description("가격")

                                )
                                .responseFields(
                                        fieldWithPath("paymentId").type(NUMBER).description("paymentId"),
                                        fieldWithPath("orderId").type(STRING).description("orderId"),
                                        fieldWithPath("orderName").type(STRING).description("주문 제목")

                                )
                                .build()
                        )));
    }

    @DisplayName("사용자는 결제 실패를 알릴 수 있다.")
    @WithMockCustomUser
    @Test
    void failPayment() throws Exception {


        mockMvc.perform(
                        post("/api/v1/payments/{paymentId}/fail",1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)

                )
                .andExpect(status().isOk())
                .andDo(document("결제 실패 요청",
                        pathParameters(
                                parameterWithName("paymentId").description("paymentId")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("결제")
                                .summary("결제 실패 요청하기")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("paymentId").type(NUMBER).description("paymentId"),
                                        fieldWithPath("orderId").type(STRING).description("orderId"),
                                        fieldWithPath("errorMessage").type(STRING).description("에러 메시지")

                                )
                                .build()
                        )));
    }


}
