package com.timeToast.timeToast.controller.event_toast;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.controller.eventToast.EventToastController;
import com.timeToast.timeToast.dto.event_toast.request.EventToastPostRequest;
import com.timeToast.timeToast.service.event_toast.EventToastService;
import com.timeToast.timeToast.service.event_toast.EventToastServiceTest;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EventControllerTest extends BaseControllerTests {

    private final EventToastService eventToastService = new EventToastServiceTest();

    @Override
    protected Object initController() {
        return new EventToastController(eventToastService);
    }

    @DisplayName("이벤트 토스트를 저장할 수 있다.")
    @WithMockCustomUser
    @Test
    void postEventToast() throws Exception {
        EventToastPostRequest eventToastPostRequest = new EventToastPostRequest(LocalDate.of(2024, 1, 1), "title", 1L);
        String json = objectMapper.writeValueAsString(eventToastPostRequest);

        mockMvc.perform(
                        post("/api/v1/eventToasts")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                                .contentType(APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("이벤트 토스트 저장",
                        resource(ResourceSnippetParameters.builder()
                                .tag("이벤트 토스트")
                                .summary("새로운 이벤트 토스트를 저장")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .requestFields(
                                        fieldWithPath("iconId").type(NUMBER).description("아이콘 Id"),
                                        fieldWithPath("title").type(STRING).description("이벤트 토스트 제목"),
                                        fieldWithPath("openedDate").type(STRING).description("토스트 개봉 날짜")
                                )
                                .responseFields()
                                .build()
                        )));
    }

    @DisplayName("자신의 이벤트 토스트를 삭제할 수 있다.")
    @WithMockCustomUser
    @Test
    void deleteFollower() throws Exception {

        mockMvc.perform(
                        delete("/api/v1/eventToasts/{eventToastId}",1)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("이벤트 토스트 삭제",
                        pathParameters(
                                parameterWithName("eventToastId").description("삭제하는 eventToastId")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("이벤트 토스트")
                                .summary("이벤트 토스트 삭제")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .build()

                        )));
    }
}
