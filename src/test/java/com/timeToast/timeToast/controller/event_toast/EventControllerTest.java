package com.timeToast.timeToast.controller.event_toast;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.annotation.JsonFormat;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
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
        EventToastPostRequest eventToastPostRequest = new EventToastPostRequest(LocalDate.of(2024, 1, 1), "title", 1L,"description");
        String json = objectMapper.writeValueAsString(eventToastPostRequest);

        mockMvc.perform(
                        post("/api/v1/eventToasts")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                                .contentType(APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("유저의 새로운 이벤트 토스트 저장",
                        resource(ResourceSnippetParameters.builder()
                                .tag("유저 - 이벤트 토스트")
                                .summary("새로운 이벤트 토스트를 저장 할 수 있다.")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .requestFields(
                                        fieldWithPath("iconId").type(NUMBER).description("아이콘 Id"),
                                        fieldWithPath("title").type(STRING).description("이벤트 토스트 제목"),
                                        fieldWithPath("openedDate").type(STRING).description("토스트 개봉 날짜"),
                                        fieldWithPath("description").type(STRING).description("설명")

                                )
                                .responseFields(
                                        fieldWithPath("id").type(NUMBER).description("저장한 이벤트 토스트의 id 값"),
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("메시지")
                                )
                                .build()
                        )));
    }

    @DisplayName("마이페이지의 이벤트 토스트 목록을 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getOwnEventToastList() throws Exception {

        mockMvc.perform(
                        get("/api/v1/eventToasts/member")
                                .header(AUTHORIZATION, TEST_ACCESS_TOKEN.value())
                )
                .andExpect(status().isOk())
                .andDo(document("로그인한 유저의 이벤트 토스트 목록 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("유저 - 이벤트 토스트")
                                .summary("로그인한 유저의 마이페이지의 이벤트 토스트 목록 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("eventToastOwnResponses[0].eventToastId").type(NUMBER).description("이벤트 토스트 id"),
                                        fieldWithPath("eventToastOwnResponses[0].title").type(STRING).description("이벤트 토스트 제목"),
                                        fieldWithPath("eventToastOwnResponses[0].openedDate").type(STRING).description("이벤트 토스트 개봉 날짜"),
                                        fieldWithPath("eventToastOwnResponses[0].icon.iconId").type(NUMBER).description("이벤트 토스트의 아이콘 id"),
                                        fieldWithPath("eventToastOwnResponses[0].icon.iconImageUrl").type(STRING).description("이벤트 토스트의 아이콘 이미지")
                                )
                                .build()
                        )));
    }

    @DisplayName("타사용자의 마이페이지 내 이벤트 토스트 목록을 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getMemberEventToastList() throws Exception {

        mockMvc.perform(
                        get("/api/v1/eventToasts/member/{memberId}", 1L)
                                .header(AUTHORIZATION, TEST_ACCESS_TOKEN.value())
                )
                .andExpect(status().isOk())
                .andDo(document("타사용자의 이벤트 토스트 목록 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("유저 - 이벤트 토스트")
                                .summary("타사용자 마이페이지의 이벤트 토스트 목록 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .pathParameters(
                                        parameterWithName("memberId").description("타유저 member id")
                                )
                                .responseFields(
                                        fieldWithPath("eventToastMemberResponses[0].eventToastId").type(NUMBER).description("이벤트 토스트 id"),
                                        fieldWithPath("eventToastMemberResponses[0].title").type(STRING).description("이벤트 토스트 제목"),
                                        fieldWithPath("eventToastMemberResponses[0].openedDate").type(STRING).description("이벤트 토스트 개봉 날짜"),
                                        fieldWithPath("eventToastMemberResponses[0].isWritten").type(BOOLEAN).description("사용자가 타사용자에게 잼을 바른 여부"),
                                        fieldWithPath("eventToastMemberResponses[0].nickname").type(STRING).description("조회 대상 사용자 닉네임"),
                                        fieldWithPath("eventToastMemberResponses[0].memberProfileUrl").type(STRING).description("조회 대상 프로필 이미지"),
                                        fieldWithPath("eventToastMemberResponses[0].icon.iconId").type(NUMBER).description("이벤트 토스트의 아이콘 id"),
                                        fieldWithPath("eventToastMemberResponses[0].icon.iconImageUrl").type(STRING).description("이벤트 토스트의 아이콘 이미지")
                                )
                                .build()
                        )));
    }


    @DisplayName("사용자가 팔로우 하고 있는 타사용자의 이벤트 토스트 목록을 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getFriendEventToastList() throws Exception {

        mockMvc.perform(
                        get("/api/v1/eventToasts/follow/following")
                                .header(AUTHORIZATION, TEST_ACCESS_TOKEN.value())
                )
                .andExpect(status().isOk())
                .andDo(document("로그인한 유저의 모든 팔로잉 유저의 이벤트 토스트 목록 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("유저 - 이벤트 토스트")
                                .summary("팔로우 하고 있는 타사용자의 이벤트 토스트 목록 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("eventToastFriendResponses[0].eventToastId").type(NUMBER).description("이벤트 토스트 id"),
                                        fieldWithPath("eventToastFriendResponses[0].title").type(STRING).description("이벤트 토스트 제목"),
                                        fieldWithPath("eventToastFriendResponses[0].openedDate").type(STRING).description("이벤트 토스트 개봉 날짜"),
                                        fieldWithPath("eventToastFriendResponses[0].nickname").type(STRING).description("조회 대상 닉네임"),
                                        fieldWithPath("eventToastFriendResponses[0].memberProfileUrl").type(STRING).description("조회 대상 프로필 이미지"),
                                        fieldWithPath("eventToastFriendResponses[0].icon.iconId").type(NUMBER).description("이벤트 토스트의 아이콘 id"),
                                        fieldWithPath("eventToastFriendResponses[0].icon.iconImageUrl").type(STRING).description("이벤트 토스트의 아이콘 이미지"),
                                        fieldWithPath("eventToastFriendResponses[0].isWritten").type(BOOLEAN).description("이벤트 토스트에 잼을 바른 여부"),
                                        fieldWithPath("eventToastFriendResponses[0].dDay").type(NUMBER).description("d-day")


                                        )
                                .build()
                        )));
    }


    @DisplayName("이벤트 토스트의 상세 정보를 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getEventToast() throws Exception {

        mockMvc.perform(
                        get("/api/v1/eventToasts/{eventToastId}", 1L)
                                .header(AUTHORIZATION, TEST_ACCESS_TOKEN.value())
                )
                .andExpect(status().isOk())
                .andDo(document("특정 이벤트 토스트 상세 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("유저 - 이벤트 토스트")
                                .summary("특정 이벤트 토스트 상세 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .pathParameters(
                                        parameterWithName("eventToastId").description("상세 조회하는 이벤트 토스트 id")
                                )
                                .responseFields(
                                        fieldWithPath("eventToastId").type(NUMBER).description("이벤트 토스트 id"),
                                        fieldWithPath("title").type(STRING).description("이벤트 토스트 제목"),
                                        fieldWithPath("openedDate").type(STRING).description("이벤트 토스트 개봉 날짜"),
                                        fieldWithPath("isOpened").type(BOOLEAN).description("이벤트 토스트 개봉 여부"),
                                        fieldWithPath("iconImageUrl").type(STRING).description("이벤트 토스트 아이콘 이미지"),
                                        fieldWithPath("memberId").type(NUMBER).description("사용자 id"),
                                        fieldWithPath("memberProfileUrl").type(STRING).description("사용자 프로필 이미지"),
                                        fieldWithPath("description").type(STRING).description("설명"),
                                        fieldWithPath("nickname").type(STRING).description("사용자 닉네임"),
                                        fieldWithPath("jamCount").type(NUMBER).description("이벤트 토스트에 발른 잼 개수"),
                                        fieldWithPath("dDay").type(NUMBER).description("이벤트 토스트 개봉까지 남은 날짜"),
                                        fieldWithPath("isWritten").type(BOOLEAN).description("이벤트 토스트에 잼을 바른 여부"),
                                        fieldWithPath("jams[].jamId").type(NUMBER).description("이벤트 토스트에 발린 잼의 id"),
                                        fieldWithPath("jams[].iconImageUrl").type(STRING).description("이벤트 토스트에 발린 잼의 아이콘 이미지"),
                                        fieldWithPath("jams[].nickname").type(STRING).description("이벤트 토스트에 잼을 바른 사용자의 닉네임")
                                )
                                .build()
                        )));

    }



    @DisplayName("자신의 이벤트 토스트를 삭제할 수 있다.")
    @WithMockCustomUser
    @Test
    void deleteEventToasts() throws Exception {

        mockMvc.perform(
                        delete("/api/v1/eventToasts/{eventToastId}",1)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("로그인한 유저의 자신의 이벤트 토스트 삭제",
                        pathParameters(
                                parameterWithName("eventToastId").description("삭제하는 eventToastId")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("유저 - 이벤트 토스트")
                                .summary("이벤트 토스트 삭제")
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

    @DisplayName("자신의 이벤트 토스트를 삭제할 수 있다. : 실패")
    @WithMockCustomUser
    @Test
    void deleteEventToastsFail() throws Exception {

        mockMvc.perform(
                        delete("/api/v1/eventToasts/{eventToastId}",2)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isNotFound())
                .andDo(document("이벤트 토스트 삭제",
                        pathParameters(
                                parameterWithName("eventToastId").description("삭제하는 eventToastId")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("유저 - 이벤트 토스트")
                                .summary("이벤트 토스트 삭제")
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