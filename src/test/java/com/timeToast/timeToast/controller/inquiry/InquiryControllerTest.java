package com.timeToast.timeToast.controller.inquiry;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.dto.inquiry.request.InquiryRequest;
import com.timeToast.timeToast.service.inquiry.InquiryService;
import com.timeToast.timeToast.service.inquiry.InquiryServiceTest;
import com.timeToast.timeToast.util.BaseControllerTests;
import com.timeToast.timeToast.util.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.timeToast.timeToast.util.TestConstant.TEST_ACCESS_TOKEN;
import static com.timeToast.timeToast.util.TestConstant.TEST_AUTH_CODE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class InquiryControllerTest extends BaseControllerTests {
    private final InquiryService inquiryService = new InquiryServiceTest();

    @Override
    protected Object initController() {
        return new InquiryController(inquiryService);
    }

    @DisplayName("문의사항을 저장할 수 있다.")
    @WithMockCustomUser
    @Test
    void saveInquiry() throws Exception {
        InquiryRequest inquiryRequest = new InquiryRequest("title", "content", "email");
        String json = objectMapper.writeValueAsString(inquiryRequest);

        mockMvc.perform(
                        post("/api/v1/inquiries")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                                .contentType(APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("문의사항 저장",
                        resource(ResourceSnippetParameters.builder()
                                .tag("문의사항")
                                .summary("새로운 문의사항 저장")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(USER_ACCESS_TOKEN)
                                )
                                .requestFields(
                                        fieldWithPath("title").type(STRING).description("문의사항 제목"),
                                        fieldWithPath("content").type(STRING).description("문의사항 내용"),
                                        fieldWithPath("email").type(STRING).description("문의자 이메일")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("메시지")
                                )
                                .build()
                        )));
    }


    @DisplayName("문의사항 목록을 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getAllInquiry() throws Exception {

        mockMvc.perform(
                        get("/api/v3/inquiries")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("문의사항 목록 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("문의사항")
                                .summary("문의사항 목록 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("inquiryResponses[0].inquiryId").type(NUMBER).description("문의사항 id"),
                                        fieldWithPath("inquiryResponses[0].title").type(STRING).description("문의사항 제목"),
                                        fieldWithPath("inquiryResponse[0].inquiryState").type(STRING).description("문의사항 작성 날짜")
                                )
                                .build()
                        )));
    }

    @DisplayName("문의사항을 상세 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getInquiryDetail() throws Exception {

        mockMvc.perform(
                        get("/api/v3/inquiries/{inquiryId}", 1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("문의 상세 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("문의사항")
                                .summary("문의사항 상세 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_AUTH_CODE.value())
                                )
                                .pathParameters(
                                        parameterWithName("inquiryId").description("상세조회할 문의사항 id")
                                )
                                .responseFields(
                                        fieldWithPath("title").type(STRING).description("문의사항 제목"),
                                        fieldWithPath("inquiryState").type(STRING).description("문의사항 해결 타입"),
                                        fieldWithPath("createdAt").type(STRING).description("문의사항 작성 날짜"),
                                        fieldWithPath("email").type(STRING).description("문의자 이메일"),
                                        fieldWithPath("content").type(STRING).description("문의사항 내용")
                                )
                                .build()
                        )));
    }

    @DisplayName("문의사항 해결 여부를 수정할 수 있다.")
    @Test
    void putInquiryState() throws Exception {

        mockMvc.perform(
                        put("/api/v3/inquiries/{inquiryId}", 1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("문의사항 상태 수정",
                        resource(ResourceSnippetParameters.builder()
                                .tag("문의사항")
                                .summary("문의사항 상태 수정")
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
}