package com.timeToast.timeToast.controller.gift_toast;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.service.gift_toast.GiftToastService;
import com.timeToast.timeToast.service.gift_toast.GiftToastServiceTest;
import com.timeToast.timeToast.util.BaseControllerTests;
import com.timeToast.timeToast.util.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.timeToast.timeToast.util.TestConstant.TEST_ACCESS_TOKEN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GiftToastManagerControllerTest extends BaseControllerTests {

    private final GiftToastService giftToastService = new GiftToastServiceTest();
    @Override
    protected Object initController() {
        return new GiftToastManagerController(giftToastService);
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
                                .tag("관리자 캡슐 토스트")
                                .summary("관리자 캡슐 토스트 목록 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("giftToastManagerResponses[0].giftToastId").type(NUMBER).description("캡슐 토스트 id"),
                                        fieldWithPath("giftToastManagerResponses[0].iconImageUrl").type(STRING).description("캡슐 토스트 아이콘 이미지"),
                                        fieldWithPath("giftToastManagerResponses[0].title").type(STRING).description("캡슐 토스트 이름"),
                                        fieldWithPath("giftToastManagerResponses[0].name").type(STRING).description("캡슐 토스트 작성 그룹")
                                )
                                .build()
                        )));
    }
}
