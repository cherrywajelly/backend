package com.timeToast.timeToast.controller.fcm;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.domain.enums.fcm.FcmConstant;
import com.timeToast.timeToast.dto.fcm.requset.FcmPostRequest;
import com.timeToast.timeToast.service.fcm.FcmService;
import com.timeToast.timeToast.service.fcm.FcmServiceTest;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FcmControllerTest extends BaseControllerTests {
    private final FcmService fcmService = new FcmServiceTest();

    @Override
    protected Object initController() {
        return new FcmController(fcmService);
    }

    @DisplayName("fcm 토큰을 저장할 수 있다.")
    @WithMockCustomUser
    @Test
    void putFcmToken() throws Exception {

        mockMvc.perform(
                        put("/api/v1/fcm?token=token")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("fcm 토큰 저장",
                        queryParameters(
                                parameterWithName("token").description("fcm 토큰")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("알림")
                                .summary("fcm 토큰 저장")
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


    @DisplayName("알림 목록을 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getResponses() throws Exception {

        mockMvc.perform(
                        get("/api/v1/fcm")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("알림 목록 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("알림")
                                .summary("알림 목록 조회")
                                .responseFields(
                                        fieldWithPath("fcmResponses[0].fcmId").type(NUMBER).description("fcm id"),
                                        fieldWithPath("fcmResponses[0].fcmConstant").type(STRING).description("fcm 타입"),
                                        fieldWithPath("fcmResponses[0].nickname").type(STRING).description("fcm 관련 사용자 닉네임"),
                                        fieldWithPath("fcmResponses[0].text").type(STRING).description("fcm 텍스트"),
                                        fieldWithPath("fcmResponses[0].imageUrl").type(STRING).description("fcm 이미지"),
                                        fieldWithPath("fcmResponses[0].time").type(STRING).description("fcm 작성 시간"),
                                        fieldWithPath("fcmResponses[0].toastName").type(STRING).description("fcm 관련 토스트 이름"),
                                        fieldWithPath("fcmResponses[0].isOpened").type(BOOLEAN).description("fcm 열어본 여부"),
                                        fieldWithPath("fcmResponses[0].param").type(NUMBER).description("이동 path param")
                                )
                                .build()
                        )));
    }

    @DisplayName("선택한 알림과 관련된 페이지로 이동할 수 있다.")
    @WithMockCustomUser
    @Test
    void putIsOpened() throws Exception {

        mockMvc.perform(
                        get("/api/v1/fcm/opened/{fcmId}", 1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("선택 알림 관련 페이지로 이동",
                        resource(ResourceSnippetParameters.builder()
                                .tag("알림")
                                .summary("알림 관련 페이지로 이동")
                                .pathParameters(
                                        parameterWithName("fcmId").description("알림 id")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("메시지")
                                )
                                .build()
                        )));
    }


    @DisplayName("알림 전송 테스트를 할 수 있다.")
    @WithMockCustomUser
    @Test
    void test() throws Exception {

        FcmPostRequest fcmResponse = new FcmPostRequest(FcmConstant.EVENTTOASTSPREAD, 1L, "toastName", 1);
        String json = objectMapper.writeValueAsString(fcmResponse);

        mockMvc.perform(
                        post("/api/v1/fcm/send")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                                .contentType(APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("알림 전송 테스트",
                        resource(ResourceSnippetParameters.builder()
                                .tag("알림")
                                .summary("알림 테스트")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .requestFields(
                                        fieldWithPath("fcmConstant").type(STRING).description("알림 타입"),
                                        fieldWithPath("senderId").type(NUMBER).description("보낸 사람 id"),
                                        fieldWithPath("toastName").type(STRING).description("알림 관련 토스트 제목"),
                                        fieldWithPath("param").type(NUMBER).description("알림 관련 id")
                                )
                                .responseFields()
                                .build()
                        )));
    }
}