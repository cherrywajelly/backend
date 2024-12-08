package com.timeToast.timeToast.controller.gift_toast;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.dto.gift_toast.request.GiftToastRequest;
import com.timeToast.timeToast.service.gift_toast.GiftToastService;
import com.timeToast.timeToast.service.gift_toast.GiftToastServiceTest;
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

public class GiftToastAdminControllerTest extends BaseControllerTests {

    private final GiftToastService giftToastService = new GiftToastServiceTest();
    @Override
    protected Object initController() {
        return new GiftToastAdminController(giftToastService);
    }

    @DisplayName("관리자는 캡슐 토스트의 목록을 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getGiftToastManager() throws Exception {

        mockMvc.perform(
                        get("/api/v3/giftToasts")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("관리자 캡슐 토스트 목록 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("관리자 - 캡슐 토스트")
                                .summary("관리자의 전체 사용자 캡슐 토스트 목록 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("giftToastManagerResponses[0].giftToastId").type(NUMBER).description("캡슐 토스트 id"),
                                        fieldWithPath("giftToastManagerResponses[0].iconImageUrl").type(STRING).description("캡슐 토스트 아이콘 이미지"),
                                        fieldWithPath("giftToastManagerResponses[0].title").type(STRING).description("캡슐 토스트 이름"),
                                        fieldWithPath("giftToastManagerResponses[0].name").type(STRING).description("캡슐 토스트 작성 그룹"),
                                        fieldWithPath("giftToastManagerResponses[0].memorizedDate").type(STRING).description("기억하고 싶은 날짜"),
                                        fieldWithPath("giftToastManagerResponses[0].openedDate").type(STRING).description("캡슐 토스트가 오픈 될 날짜"),
                                        fieldWithPath("giftToastManagerResponses[0].isOpened").type(BOOLEAN).description("캡슐 토스트 오픈 여부"),
                                        fieldWithPath("giftToastManagerResponses[0].giftToastType").type(STRING).description("캡슐 토스트 타압 (GROUP | FRIEND | MINE)"),
                                        fieldWithPath("giftToastManagerResponses[0].createdAt").type(STRING).description("캡슐 토스트 생성 날짜")
                                )
                                .build()
                        )));
    }

    @DisplayName("관리자는 캡슐 토스트를 상세 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getGiftToastInfo() throws Exception {

        mockMvc.perform(
                        get("/api/v3/giftToasts/{giftToastId}", 1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("관리자 캡슐 토스트 상세 조회",
                        pathParameters(
                                parameterWithName("giftToastId").description("켑슐 토스트 Id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("관리자 - 캡슐 토스트")
                                .summary("관리자의 캡슐 토스트 상세 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("giftToastId").type(NUMBER).description("캡슐 토스트 id"),
                                        fieldWithPath("iconImageUrl").type(STRING).description("캡슐 토스트 아이콘 이미지"),
                                        fieldWithPath("title").type(STRING).description("캡슐 토스트 이름"),
                                        fieldWithPath("name").type(STRING).description("캡슐 토스트 작성 그룹 이름"),
                                        fieldWithPath("memorizedDate").type(STRING).description("기억하고 싶은 날짜"),
                                        fieldWithPath("openedDate").type(STRING).description("캡슐 토스트가 오픈 될 날짜"),
                                        fieldWithPath("isOpened").type(BOOLEAN).description("캡슐 토스트 오픈 여부"),
                                        fieldWithPath("giftToastType").type(STRING).description("캡슐 토스트 타압 (GROUP | FRIEND | MINE)"),
                                        fieldWithPath("createdAt").type(STRING).description("캡슐 토스트 생성 날짜"),
                                        fieldWithPath("toastPieceManagerResponses[0].toastPieceId").type(NUMBER).description("캡슐 토스트 조각 id"),
                                        fieldWithPath("toastPieceManagerResponses[0].iconImageUrl").type(STRING).description("캡슐 토스트 조각 아이콘 이미지 ㅕrl"),
                                        fieldWithPath("toastPieceManagerResponses[0].title").type(STRING).description("캡슐 토스트 조각 제목"),
                                        fieldWithPath("toastPieceManagerResponses[0].createdAt").type(STRING).description("캡슐 토스트 조각 생성 날짜"),
                                        fieldWithPath("toastPieceManagerResponses[0].nickname").type(STRING).description("캡슐 토스트 조각 작성자 닉네임")
                                )
                                .build()
                        )));
    }


    @DisplayName("관리자는 캡슐 토스트 정보를 수정할 수 있다.")
    @WithMockCustomUser
    @Test
    void editGiftToast() throws Exception {
        GiftToastRequest giftToastRequest = new GiftToastRequest(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 1), true);
        String json = objectMapper.writeValueAsString(giftToastRequest);

        mockMvc.perform(
                        put("/api/v3/giftToasts/{giftToastId}",1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                                .contentType(APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("캡슐 토스트 정보 수정",
                        resource(ResourceSnippetParameters.builder()
                                .tag("관리자 - 캡슐 토스트")
                                .summary("관리자의 캡슐 토스트 수정")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .requestFields(
                                        fieldWithPath("memorizedDate").type(STRING).description("캡슐 토스트 이벤트 날짜"),
                                        fieldWithPath("openedDate").type(STRING).description("캡슐 토스트 오픈 날짜"),
                                        fieldWithPath("isOpened").type(BOOLEAN).description("캡슐 토스트 열림 여부")
                                )
                                .responseFields(
                                        fieldWithPath("memorizedDate").type(ARRAY).description("캡슐 토스트 이벤트 날짜"),
                                        fieldWithPath("openedDate").type(ARRAY).description("캡슐 토스트 오픈 날짜"),
                                        fieldWithPath("isOpened").type(BOOLEAN).description("캡슐 토스트 열림 여부")
                                )
                                .build()
                        )));
    }
}
