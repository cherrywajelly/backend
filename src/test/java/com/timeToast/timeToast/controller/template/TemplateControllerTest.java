package com.timeToast.timeToast.controller.template;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.dto.template.request.TemplateSaveRequest;
import com.timeToast.timeToast.service.template.TemplateService;
import com.timeToast.timeToast.service.template.TemplateServiceTest;
import com.timeToast.timeToast.util.BaseControllerTests;
import com.timeToast.timeToast.util.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.timeToast.timeToast.util.TestConstant.TEST_ACCESS_TOKEN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TemplateControllerTest extends BaseControllerTests {
    private final TemplateService templateService = new TemplateServiceTest();

    @Override
    protected Object initController() {
        return new TemplateController(templateService);
    }

    @DisplayName("공유 템플릿 텍스트를 작성할 수 있다.")
    @WithMockCustomUser
    @Test
    void saveTemplate() throws Exception {
        TemplateSaveRequest templateSaveRequest = new TemplateSaveRequest(1L, "text");
        String json = objectMapper.writeValueAsString(templateSaveRequest);

        mockMvc.perform(
                post("/api/v1/templates")
                        .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("공유 템플릿 텍스트 작성",
                        resource(ResourceSnippetParameters.builder()
                                .tag("공유 템플릿")
                                .summary("공유 템플릿 작성")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .requestFields(
                                        fieldWithPath("eventToastId").type(NUMBER).description("공유할 이벤트 토스트 id"),
                                        fieldWithPath("text").type(STRING).description("작성할 텍스트")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("메시지")
                                )
                                .build()
                        )));
    }

    @DisplayName("공유 템플릿을 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getTemplate() throws Exception {

        mockMvc.perform(
                        get("/api/v1/templates/{eventToastId}", 1L)
                                .header(AUTHORIZATION, TEST_ACCESS_TOKEN.value())
                )
                .andExpect(status().isOk())
                .andDo(document("공유 템플릿 목록 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("공유 템플릿")
                                .summary("공유 템플릿 목록 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .pathParameters(
                                        parameterWithName("eventToastId").description("공유할 이벤트 토스트 id")
                                )
                                .responseFields(
                                        fieldWithPath("eventToastTemplateResponse.title").type(STRING).description("공유할 이벤트 토스트 제목"),
                                        fieldWithPath("eventToastTemplateResponse.openedDate").type(STRING).description("공유할 이벤트 토스트 개봉 날짜"),
                                        fieldWithPath("iconImageUrl").type(STRING).description("공유할 이벤트 토스트 아이콘 이미지"),
                                        fieldWithPath("profileImageUrl").type(STRING).description("이벤트 토스트 제작자 프로필 이미지"),
                                        fieldWithPath("nickname").type(STRING).description("이벤트 토스트 제작자 닉네임"),
                                        fieldWithPath("text").type(STRING).description("공유할 텍스트")
                                )
                                .build()
                        )));
    }
}