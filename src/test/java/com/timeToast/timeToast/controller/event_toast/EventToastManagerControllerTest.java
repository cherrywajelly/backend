package com.timeToast.timeToast.controller.event_toast;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.controller.eventToast.EventToastManagerController;
import com.timeToast.timeToast.dto.event_toast.request.EventToastPostRequest;
import com.timeToast.timeToast.dto.event_toast.request.EventToastRequest;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EventToastManagerControllerTest extends BaseControllerTests {
    private final EventToastService eventToastService = new EventToastServiceTest();

    @Override
    protected Object initController() {
        return new EventToastManagerController(eventToastService);
    }

    @DisplayName("관리자는 이벤트 토스트의 목록을 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getEventToastsManager() throws Exception {

        mockMvc.perform(
                        get("/api/v3/eventToasts")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("관리자 이벤트 토스트 목록 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("관리자 이벤트 토스트")
                                .summary("관리자 이벤트 토스트 목록 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("eventToastManagerResponses[0].eventToastId").type(NUMBER).description("이벤트 토스트 id"),
                                        fieldWithPath("eventToastManagerResponses[0].iconImageUrl").type(STRING).description("이벤트 토스트 아이콘 이미지"),
                                        fieldWithPath("eventToastManagerResponses[0].title").type(STRING).description("이벤트 토스트 이름"),
                                        fieldWithPath("eventToastManagerResponses[0].nickname").type(STRING).description("이벤트 토스트 작성자 닉네임")
                                )
                                .build()
                        )));
    }

    @DisplayName("관리자는 이벤트 토스트의 상세 정보를 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getEventToastInfoManager() throws Exception {

        mockMvc.perform(
                        get("/api/v3/eventToasts/{eventToastId}",1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("관리자 이벤트 토스트 상세 조회",
                        pathParameters(
                                parameterWithName("eventToastId").description("이벤트 토스트 Id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("관리자 이벤트 토스트")
                                .summary("관리자 이벤트 토스트 상세 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("eventToastId").type(NUMBER).description("이벤트 토스트 id"),
                                        fieldWithPath("iconImageUrl").type(STRING).description("이벤트 토스트 아이콘 이미지"),
                                        fieldWithPath("title").type(STRING).description("이벤트 토스트 이름"),
                                        fieldWithPath("nickname").type(STRING).description("이벤트 토스트 작성자 닉네임"),
                                        fieldWithPath("openedDate").type(ARRAY).description("이벤트 토스트 오픈 날짜"),
                                        fieldWithPath("isOpened").type(BOOLEAN).description("이벤트 토스트 열림 여부"),
                                        fieldWithPath("createdAt").type(STRING).description("이벤트 토스트 생성 날짜"),
                                        fieldWithPath("jamManagerResponses[0].jamId").type(NUMBER).description("이벤트 토스트의 잼 id"),
                                        fieldWithPath("jamManagerResponses[0].iconImageUrl").type(STRING).description("이벤트 토스트의 잼 이미지 url"),
                                        fieldWithPath("jamManagerResponses[0].title").type(STRING).description("이벤트 토스트의 잼 제목"),
                                        fieldWithPath("jamManagerResponses[0].createdAt").type(STRING).description("이벤트 토스트의 잼 작성날짜"),
                                        fieldWithPath("jamManagerResponses[0].nickname").type(STRING).description("이벤트 토스트의 잼 작성자 닉네임")
                                )
                                .build()
                        )));
    }

    @DisplayName("관리자는 이벤트 토스트 정보를 수정할 수 있다.")
    @WithMockCustomUser
    @Test
    void editEventToast() throws Exception {
        EventToastRequest eventToastRequest = new EventToastRequest(LocalDate.of(2024, 1, 1), true);
        String json = objectMapper.writeValueAsString(eventToastRequest);

        mockMvc.perform(
                        put("/api/v3/eventToasts/{eventToastId}",1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                                .contentType(APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("이벤트 토스트 정보 수정",
                        resource(ResourceSnippetParameters.builder()
                                .tag("이벤트 토스트")
                                .summary("이벤트 토스트 수정")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .requestFields(
                                        fieldWithPath("openedDate").type(STRING).description("이벤트 토스트 오픈 날짜"),
                                        fieldWithPath("isOpened").type(BOOLEAN).description("이벤트 토스트 열림 여부")
                                )
                                .responseFields(
                                        fieldWithPath("openedDate").type(ARRAY).description("이벤트 토스트 오픈 날짜"),
                                        fieldWithPath("isOpened").type(BOOLEAN).description("이벤트 토스트 열림 여부")
                                )
                                .build()
                        )));
    }
}
