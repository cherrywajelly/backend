package com.timeToast.timeToast.controller.gift_toast;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.dto.gift_toast.request.GiftToastFriendRequest;
import com.timeToast.timeToast.dto.gift_toast.request.GiftToastGroupRequest;
import com.timeToast.timeToast.dto.gift_toast.request.GiftToastMineRequest;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GiftToastControllerTest extends BaseControllerTests {

    private final GiftToastService giftToastService = new GiftToastServiceTest();
    @Override
    protected Object initController() {
        return new GiftToastController(giftToastService);
    }

    @DisplayName("group 선물 토스트를 저장할 수 있다.")
    @WithMockCustomUser
    @Test
    void saveGiftToastGroup() throws Exception {

        GiftToastGroupRequest giftToastGroupRequest = new GiftToastGroupRequest(1L, 1L,LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 1), "title");
        String json = objectMapper.writeValueAsString(giftToastGroupRequest);

        mockMvc.perform(
                        post("/api/v1/giftToasts/group")
                                .header(AUTHORIZATION, TEST_ACCESS_TOKEN.value())
                                .contentType(APPLICATION_JSON)
                                .content(json)

                )
                .andExpect(status().isOk())
                .andDo(document("선물 토스트 저장 - 그룹",
                        resource(ResourceSnippetParameters.builder()
                                .tag("선물 토스트")
                                .summary("선물 토스트 저장 - 그룹")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .requestFields(
                                        fieldWithPath("iconId").type(NUMBER).description("아이콘 Id"),
                                        fieldWithPath("teamId").type(NUMBER).description("팀 Id"),
                                        fieldWithPath("memorizedDate").type(STRING).description("memorized date"),
                                        fieldWithPath("openedDate").type(STRING).description("opened date"),
                                        fieldWithPath("title").type(STRING).description("제목")
                                )
                                .responseFields(
                                        fieldWithPath("giftToastId").type(NUMBER).description("선물 토스트 Id"),
                                        fieldWithPath("title").type(STRING).description("제목"),
                                        fieldWithPath("giftToastType").type(STRING).description("선물 토스트 타입"),
                                        fieldWithPath("memorizedDate").type(STRING).description("memorized date"),
                                        fieldWithPath("openedDate").type(STRING).description("opened date"),
                                        fieldWithPath("isOpened").type(BOOLEAN).description("open 여부")
                                )
                                .build()
                        )));
    }
    @DisplayName("friend 선물 토스트를 저장할 수 있다.")
    @WithMockCustomUser
    @Test
    void saveGiftToastFriend() throws Exception {

        GiftToastFriendRequest giftToastFriendRequest = new GiftToastFriendRequest(1L, 1L, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 1), "title");
        String json = objectMapper.writeValueAsString(giftToastFriendRequest);

        mockMvc.perform(
                        post("/api/v1/giftToasts/friend")
                                .header(AUTHORIZATION, TEST_ACCESS_TOKEN.value())
                                .contentType(APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("선물 토스트 저장 - 팔로잉",
                        resource(ResourceSnippetParameters.builder()
                                .tag("선물 토스트")
                                .summary("선물 토스트 저장 - 팔로잉")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .requestFields(
                                        fieldWithPath("iconId").type(NUMBER).description("아이콘 Id"),
                                        fieldWithPath("friendId").type(NUMBER).description("팔로잉 Id"),
                                        fieldWithPath("memorizedDate").type(STRING).description("memorized date"),
                                        fieldWithPath("openedDate").type(STRING).description("opened date"),
                                        fieldWithPath("title").type(STRING).description("제목")
                                )
                                .responseFields(
                                        fieldWithPath("giftToastId").type(NUMBER).description("선물 토스트 Id"),
                                        fieldWithPath("title").type(STRING).description("제목"),
                                        fieldWithPath("giftToastType").type(STRING).description("선물 토스트 타입"),
                                        fieldWithPath("memorizedDate").type(STRING).description("memorized date"),
                                        fieldWithPath("openedDate").type(STRING).description("opened date"),
                                        fieldWithPath("isOpened").type(BOOLEAN).description("open 여부")
                                )
                                .build()
                        )));
    }
    @DisplayName("mine 선물 토스트를 저장할 수 있다.")
    @WithMockCustomUser
    @Test
    void saveGiftToastMine() throws Exception {

        GiftToastMineRequest giftToastMineRequest = new GiftToastMineRequest(1L,  LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 1), "title");
        String json = objectMapper.writeValueAsString(giftToastMineRequest);

        mockMvc.perform(
                        post("/api/v1/giftToasts/mine")
                                .header(AUTHORIZATION, TEST_ACCESS_TOKEN.value())
                                .contentType(APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("선물 토스트 저장 - 나에게",
                        resource(ResourceSnippetParameters.builder()
                                .tag("선물 토스트")
                                .summary("선물 토스트 저장 - 나에게")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .requestFields(
                                        fieldWithPath("iconId").type(NUMBER).description("아이콘 Id"),
                                        fieldWithPath("memorizedDate").type(STRING).description("memorized date"),
                                        fieldWithPath("openedDate").type(STRING).description("opened date"),
                                        fieldWithPath("title").type(STRING).description("제목")
                                )
                                .responseFields(
                                        fieldWithPath("giftToastId").type(NUMBER).description("선물 토스트 Id"),
                                        fieldWithPath("title").type(STRING).description("제목"),
                                        fieldWithPath("giftToastType").type(STRING).description("선물 토스트 타입"),
                                        fieldWithPath("memorizedDate").type(STRING).description("memorized date"),
                                        fieldWithPath("openedDate").type(STRING).description("opened date"),
                                        fieldWithPath("isOpened").type(BOOLEAN).description("open 여부")
                                )
                                .build()
                        )));
    }

}