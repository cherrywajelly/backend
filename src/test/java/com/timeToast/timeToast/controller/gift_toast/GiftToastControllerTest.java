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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
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

        GiftToastGroupRequest giftToastGroupRequest = new GiftToastGroupRequest(1L, 1L,LocalDate.now(), LocalDate.now(), "title");
        String json = objectMapper.writeValueAsString(giftToastGroupRequest);

        mockMvc.perform(
                        post("/api/v1/giftToasts/group")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                                .contentType(APPLICATION_JSON)
                                .content(json)

                )
                .andExpect(status().isOk())
                .andDo(document("선물 토스트 저장 - 그룹 성공",
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

    @DisplayName("group 선물 토스트를 저장할 수 있다.-실패: 자신의 그룹이 아닌 경우")
    @WithMockCustomUser
    @Test
    void saveGiftToastGroupFail() throws Exception {

        GiftToastGroupRequest giftToastGroupRequest = new GiftToastGroupRequest(1L, 2L,LocalDate.now(), LocalDate.now(), "title");
        String json = objectMapper.writeValueAsString(giftToastGroupRequest);

        mockMvc.perform(
                        post("/api/v1/giftToasts/group")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                                .contentType(APPLICATION_JSON)
                                .content(json)

                )
                .andExpect(status().isBadRequest())
                .andDo(document("선물 토스트 저장 - 그룹 실패: 자신의 그룹이 아닌 경우",
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
                                        fieldWithPath("statusCode").type(STRING).description("400"),
                                        fieldWithPath("message").type(STRING).description("잘못된 선물 토스트 형식입니다.")
                                )
                                .build()
                        )));
    }

    @DisplayName("group 선물 토스트를 저장할 수 있다.- 실패: 잘못된 openedDate")
    @WithMockCustomUser
    @Test
    void saveGiftToastGroupDateFail() throws Exception {

        GiftToastGroupRequest giftToastGroupRequest = new GiftToastGroupRequest(1L, 2L,LocalDate.now(), LocalDate.now().minusDays(1), "title");
        String json = objectMapper.writeValueAsString(giftToastGroupRequest);

        mockMvc.perform(
                        post("/api/v1/giftToasts/group")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                                .contentType(APPLICATION_JSON)
                                .content(json)

                )
                .andExpect(status().isBadRequest())
                .andDo(document("선물 토스트 저장 - 그룹 실패: 잘못된 openedDate",
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
                                        fieldWithPath("statusCode").type(STRING).description("400"),
                                        fieldWithPath("message").type(STRING).description("잘못된 선물 토스트 형식입니다.")
                                )
                                .build()
                        )));
    }

    @DisplayName("friend 선물 토스트를 저장할 수 있다.- 실패: 잘못된 openedDate")
    @WithMockCustomUser
    @Test
    void saveGiftToastFriendDateFail() throws Exception {

        GiftToastFriendRequest giftToastFriendRequest = new GiftToastFriendRequest(1L, 1L, LocalDate.now(), LocalDate.now().minusDays(1),  "title");
        String json = objectMapper.writeValueAsString(giftToastFriendRequest);

        mockMvc.perform(
                        post("/api/v1/giftToasts/friend")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                                .contentType(APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isBadRequest())
                .andDo(document("선물 토스트 저장 - 팔로잉 실패: 잘못된 openedDate",
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
                                        fieldWithPath("statusCode").type(STRING).description("400"),
                                        fieldWithPath("message").type(STRING).description("잘못된 선물 토스트 형식입니다.")
                                )
                                .build()
                        )));
    }

    @DisplayName("friend 선물 토스트를 저장할 수 있다.")
    @WithMockCustomUser
    @Test
    void saveGiftToastFriend() throws Exception {

        GiftToastFriendRequest giftToastFriendRequest = new GiftToastFriendRequest(1L, 1L, LocalDate.now(), LocalDate.now(), "title");
        String json = objectMapper.writeValueAsString(giftToastFriendRequest);

        mockMvc.perform(
                        post("/api/v1/giftToasts/friend")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                                .contentType(APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("선물 토스트 저장 - 팔로잉 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("선물 토스트")
                                .summary("선물 토스트 저장 - 팔로잉 ")
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


    @DisplayName("mine 선물 토스트를 저장할 수 있다. - 실패: 잘못된 openedDate")
    @WithMockCustomUser
    @Test
    void saveGiftToastMineDateFail() throws Exception {

        GiftToastMineRequest giftToastMineRequest = new GiftToastMineRequest(1L,  LocalDate.now(), LocalDate.now().minusDays(1),"title");
        String json = objectMapper.writeValueAsString(giftToastMineRequest);

        mockMvc.perform(
                        post("/api/v1/giftToasts/mine")
                                .header(AUTHORIZATION,USER_ACCESS_TOKEN)
                                .contentType(APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isBadRequest())
                .andDo(document("선물 토스트 저장 - 나에게  실패: 잘못된 openedDate",
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
                                        fieldWithPath("statusCode").type(STRING).description("400"),
                                        fieldWithPath("message").type(STRING).description("잘못된 선물 토스트 형식입니다.")
                                )
                                .build()
                        )));
    }

    @DisplayName("mine 선물 토스트를 저장할 수 있다.")
    @WithMockCustomUser
    @Test
    void saveGiftToastMine() throws Exception {

        GiftToastMineRequest giftToastMineRequest = new GiftToastMineRequest(1L,  LocalDate.now(), LocalDate.now(), "title");
        String json = objectMapper.writeValueAsString(giftToastMineRequest);

        mockMvc.perform(
                        post("/api/v1/giftToasts/mine")
                                .header(AUTHORIZATION,USER_ACCESS_TOKEN)
                                .contentType(APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("선물 토스트 저장 - 나에게 성공",
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

    @DisplayName("선물 토스트 단일 상세 조회를 할 수 있다.")
    @WithMockCustomUser
    @Test
    void getGiftToast() throws Exception {


        mockMvc.perform(
                        get("/api/v1/giftToasts/{giftToastId}", 1L)
                                .header(AUTHORIZATION,USER_ACCESS_TOKEN)

                )
                .andExpect(status().isOk())
                .andDo(document("선물 토스트 단일 상세 조회 성공",
                        pathParameters(
                                parameterWithName("giftToastId").description("giftToast Id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("선물 토스트")
                                .summary("선물 토스트 단일 상세 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("giftToastInfo.giftToastId").type(NUMBER).description("선물 토스트 Id"),
                                        fieldWithPath("giftToastInfo.title").type(STRING).description("제목"),
                                        fieldWithPath("giftToastInfo.iconImageUrl").type(STRING).description("아이콘 image url"),
                                        fieldWithPath("giftToastInfo.giftToastType").type(STRING).description("선물 토스트 타입"),
                                        fieldWithPath("giftToastInfo.giftToastOwner").type(STRING).description("선물 토스트 소유주 이름"),
                                        fieldWithPath("giftToastInfo.profileImageUrl").type(STRING).description("선물 토스트 프로필 사진 url"),
                                        fieldWithPath("giftToastInfo.memorizedDate").type(STRING).description("memorized date"),
                                        fieldWithPath("giftToastInfo.openedDate").type(STRING).description("opened date"),
                                        fieldWithPath("giftToastInfo.createdDate").type(STRING).description("created date"),
                                        fieldWithPath("giftToastInfo.isOpened").type(BOOLEAN).description("open 여부"),
                                        fieldWithPath("dDay").type(NUMBER).description("D-day"),
                                        fieldWithPath("toastPieceResponses.giftToastId").type(NUMBER).description("giftToastId"),
                                        fieldWithPath("toastPieceResponses.toastPieceResponses[0].memberId").type(NUMBER).description("member id"),
                                        fieldWithPath("toastPieceResponses.toastPieceResponses[0].toastPieceId").type(NUMBER).description("toastPiece id"),
                                        fieldWithPath("toastPieceResponses.toastPieceResponses[0].nickname").type(STRING).description("nickname"),
                                        fieldWithPath("toastPieceResponses.toastPieceResponses[0].profileUrl").type(STRING).description("profile url"),
                                        fieldWithPath("toastPieceResponses.toastPieceResponses[0].iconImageUrl").type(STRING).description("icon image url"),
                                        fieldWithPath("toastPieceResponses.toastPieceResponses[0].title").type(STRING).description("title"),
                                        fieldWithPath("toastPieceResponses.toastPieceResponses[0].contentsUrl").type(STRING).description("contents url"),
                                        fieldWithPath("toastPieceResponses.toastPieceResponses[0].createdAt").type(STRING).description("created at"),
                                        fieldWithPath("toastPieceResponses.toastPieceResponses[0].toastPieceImages[0]").type(ARRAY).description("토스트의 이미지 리스트")

                                        )
                                .build()
                        )));
    }

    @DisplayName("선물 토스트 단일 상세 조회를 할 수 있다. - 실패: 찾을 수 없음. ")
    @WithMockCustomUser
    @Test
    void getGiftToastFail() throws Exception {


        mockMvc.perform(
                        get("/api/v1/giftToasts/{giftToastId}", 2L)
                                .header(AUTHORIZATION,USER_ACCESS_TOKEN)

                )
                .andExpect(status().isNotFound())
                .andDo(document("선물 토스트 단일 상세 조회 실패",
                        pathParameters(
                                parameterWithName("giftToastId").description("giftToast Id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("선물 토스트")
                                .summary("선물 토스트 단일 상세 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("404"),
                                        fieldWithPath("message").type(STRING).description("선물 토스트를 찾을 수 없습니다.")
                                )
                                .build()
                        )));
    }

    @DisplayName("로그인한 사용자가 가진 선물 토스트 리스트를 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getGiftToastByLogin() throws Exception {


        mockMvc.perform(
                        get("/api/v1/giftToasts/members")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)

                )
                .andExpect(status().isOk())
                .andDo(document("로그인한 사용자의 선물 토스트 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("선물 토스트")
                                .summary("로그인한 사용자의 선물 토스트 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("giftToastResponses[0].giftToastId").type(NUMBER).description("giftToastId"),
                                        fieldWithPath("giftToastResponses[0].title").type(STRING).description("제목"),
                                        fieldWithPath("giftToastResponses[0].iconImageUrl").type(STRING).description("이미지 url"),
                                        fieldWithPath("giftToastResponses[0].giftToastType").type(STRING).description("선물 토스트 타입"),
                                        fieldWithPath("giftToastResponses[0].giftToastOwner").type(STRING).description("선물 토스트 소유주 이름"),
                                        fieldWithPath("giftToastResponses[0].isOpened").type(BOOLEAN).description("오픈 여부")

                                )
                                .build()
                        )));
    }

    @DisplayName("로그인한 사용자가 작성 할 선물 토스트 리스트를 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getGiftToastIncomplete() throws Exception {


        mockMvc.perform(
                        get("/api/v1/giftToasts/members/incomplete")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)

                )
                .andExpect(status().isOk())
                .andDo(document("로그인한 사용자의 작성 할 선물 토스트 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("선물 토스트")
                                .summary("로그인한 사용자의 작성 할 선물 토스트 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("giftToastResponses[0].giftToastId").type(NUMBER).description("giftToastId"),
                                        fieldWithPath("giftToastResponses[0].title").type(STRING).description("제목"),
                                        fieldWithPath("giftToastResponses[0].iconImageUrl").type(STRING).description("이미지 url")

                                )
                                .build()
                        )));
    }

    @DisplayName("자신의 선물 토스트를 삭제할 수 있다.")
    @WithMockCustomUser
    @Test
    void deleteGiftToast() throws Exception {


        mockMvc.perform(
                        delete("/api/v1/giftToasts/{giftToastId}", 1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)

                )
                .andExpect(status().isOk())

                .andDo(document("로그인한 사용자가 자신의 선물 토스트를 삭제",
                        pathParameters(
                                parameterWithName("giftToastId").description("giftToast Id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("선물 토스트")
                                .summary("선물 토스트 삭제")
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