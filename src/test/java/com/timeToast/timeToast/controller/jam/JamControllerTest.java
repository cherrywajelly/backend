package com.timeToast.timeToast.controller.jam;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.domain.enums.fcm.FcmConstant;
import com.timeToast.timeToast.dto.fcm.response.FcmResponse;
import com.timeToast.timeToast.dto.jam.request.JamRequest;
import com.timeToast.timeToast.dto.toast_piece.request.ToastPieceRequest;
import com.timeToast.timeToast.service.jam.JamService;
import com.timeToast.timeToast.service.jam.JamServiceTest;
import com.timeToast.timeToast.util.BaseControllerTests;
import com.timeToast.timeToast.util.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDate;
import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.timeToast.timeToast.util.TestConstant.TEST_ACCESS_TOKEN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class JamControllerTest extends BaseControllerTests {

    private final JamService jamService = new JamServiceTest();

    @Override
    protected Object initController() {
        return new JamController(jamService);
    }

    @DisplayName("다른 사용자의 이벤트 토스트에 잼을 바를 수 있다.")
    @WithMockCustomUser
    @Test
    void postJam() throws Exception {
        JamRequest jamRequest = new JamRequest("title", 1L);
        String json = objectMapper.writeValueAsString(jamRequest);

        mockMvc.perform(
                        multipart("/api/v1/jams/{eventToastId}", 1L )
                                .file("jamContents", "jamText.png".getBytes())
                                .file("jamImages", "jamImage.png".getBytes())
                                .file(new MockMultipartFile("jamRequest", "jamRequest", MediaType.APPLICATION_JSON_VALUE, json.getBytes()))
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(status().isOk())
                .andDo(document("잼 저장",
                        requestParts(
                                partWithName("jamContents").description("잼 내용 파일"),
                                partWithName("jamImages").description("잼 이미지 파일"),
                                partWithName("jamRequest").description("잼 요청 정보")
                        ),
                        requestPartBody("jamRequest"), // JSON 필드 문서화
                        requestPartFields("jamRequest",
                                fieldWithPath("title").type(STRING).description("잼 제목"),
                                fieldWithPath("iconId").type(NUMBER).description("잼 아이콘 id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("잼")
                                .summary("잼 저장")
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

    @DisplayName("이벤트 토스트에 관련된 잼 목록을 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getJams() throws Exception {

        mockMvc.perform(
                        get("/api/v1/jams/eventToast/{eventToastId}", 1L)
                                .header(AUTHORIZATION, TEST_ACCESS_TOKEN.value())
                )
                .andExpect(status().isOk())
                .andDo(document("잼 목록 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("잼")
                                .summary("잼 목록 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .pathParameters(
                                        parameterWithName("eventToastId").description("조회할 이벤트 토스트 id")
                                )
                                .responseFields(
                                        fieldWithPath("[].jamId").type(NUMBER).description("잼 id"),
                                        fieldWithPath("[].iconImageUrl").type(STRING).description("잼 아이콘 이미지"),
                                        fieldWithPath("[].nickname").type(STRING).description("잼 작성자 닉네임")
                                )
                                .build()
                        )));
    }


    @DisplayName("이벤트 토스트에 관련된 잼을 상세 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getJam() throws Exception {

        mockMvc.perform(
                        get("/api/v1/jams/{jamId}", 1L)
                                .header(AUTHORIZATION, TEST_ACCESS_TOKEN.value())
                )
                .andExpect(status().isOk())
                .andDo(document("잼 상세 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("잼")
                                .summary("잼 상세 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .pathParameters(
                                        parameterWithName("jamId").description("상세 조회할 잼 id")
                                )
                                .responseFields(
                                        fieldWithPath("eventToastDataResponse.eventToastTitle").type(STRING).description("이벤트 토스트 제목"),
                                        fieldWithPath("eventToastDataResponse.eventToastMemberProfile").type(STRING).description("이벤트 토스트 작성자 프로필"),
                                        fieldWithPath("eventToastDataResponse.eventToastNickname").type(STRING).description("이벤트 토스트 작성자 닉네임"),
                                        fieldWithPath("eventToastDataResponse.eventToastIconImageUrl").type(STRING).description("이벤트 토스트 아이콘 이미지"),
                                        fieldWithPath("jamDataResponse.jamIconImageUrl").type(STRING).description("잼 아이콘 이미지"),
                                        fieldWithPath("jamDataResponse.jamTitle").type(STRING).description("잼 제목"),
                                        fieldWithPath("jamDataResponse.jamMemberProfileUrl").type(STRING).description("잼 작성자 프로필 이미지"),
                                        fieldWithPath("jamDataResponse.jamNickname").type(STRING).description("잼 작성자 닉네임"),
                                        fieldWithPath("jamDataResponse.jamContentsUrl").type(STRING).description("잼 컨텐츠 파일"),
                                        fieldWithPath("jamDataResponse.jamImageUrl").type(STRING).description("잼 이미지 파일"),
                                        fieldWithPath("jamDataResponse.jamCreatedDate").type(STRING).description("잼 작성 일자")
                                )
                                .build()
                        )));
    }

    @DisplayName("자신의 잼을 삭제할 수 있다.")
    @WithMockCustomUser
    @Test
    void deleteJam() throws Exception {

        mockMvc.perform(
                        delete("/api/v1/jams/{jamId}",1)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("잼 삭제",
                        pathParameters(
                                parameterWithName("jamId").description("삭제하는 jam id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("잼")
                                .summary("잼 삭제")
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