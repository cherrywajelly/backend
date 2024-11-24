package com.timeToast.timeToast.controller.inquiry;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.dto.event_toast.request.EventToastPostRequest;
import com.timeToast.timeToast.dto.inquiry.request.InquiryRequest;
import com.timeToast.timeToast.dto.inquiry.response.InquiryDetailResponse;
import com.timeToast.timeToast.dto.inquiry.response.InquiryResponses;
import com.timeToast.timeToast.service.inquiry.InquiryService;
import com.timeToast.timeToast.service.inquiry.InquiryServiceTest;
import com.timeToast.timeToast.util.BaseControllerTests;
import com.timeToast.timeToast.util.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.time.LocalDate;

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
                                .tag("문의")
                                .summary("새로운 문의사항 저장")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .requestFields(
                                        fieldWithPath("title").type(NUMBER).description("문의사항 제목"),
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
}

//@GetMapping("")
//public InquiryResponses getAllInquiry() {
//    return inquiryService.getInquiry();
//}
//
//@GetMapping("/{inquiryId}")
//public InquiryDetailResponse getInquiryDetail(@PathVariable int inquiryId) {
//    return inquiryService.getInquiryDetail(inquiryId);
//}
//
//@PutMapping("/{inquiryId}")
//public String putInquiryState(