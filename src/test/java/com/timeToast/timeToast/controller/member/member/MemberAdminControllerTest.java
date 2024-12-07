package com.timeToast.timeToast.controller.member.member;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.service.member.member.*;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberAdminControllerTest extends BaseControllerTests {
    private final ManagerService managerService = new ManagerServiceTest();

    @Override
    protected Object initController() {
        return new MemberAdminController(managerService);
    }

    @DisplayName("최고 관리자는 staff로 지정할 수 있다.")
    @WithMockCustomUser
    @Test
    void saveToStaff() throws Exception {

        mockMvc.perform(
                        post("/api/v4/members/{memberId}/staffs", 1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("최고 관리자는 staff로 role 변경",
                        pathParameters(
                                parameterWithName("memberId").description("조회 대상의 memberId")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("관리자 사용자")
                                .summary("관리자가 staff로 role 변경")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("memberId").type(NUMBER).description("사용자 id"),
                                        fieldWithPath("memberProfileUrl").type(STRING).description("사용자 프로필 이미지 url"),
                                        fieldWithPath("nickname").type(STRING).description("사용자 닉네임"),
                                        fieldWithPath("memberRole").type(STRING).description("사용자 role")

                                )
                                .build()
                        )));
    }

    @DisplayName("최고 관리자는 creator로 지정할 수 있다.")
    @WithMockCustomUser
    @Test
    void saveCreatorInfo() throws Exception {

        mockMvc.perform(
                        post("/api/v4/members/{memberId}/creators", 1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("최고 관리자는 creator로 role 변경",
                        pathParameters(
                                parameterWithName("memberId").description("조회 대상의 memberId")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("관리자 사용자")
                                .summary("관리자가 creator로 role 변경")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("memberId").type(NUMBER).description("사용자 id"),
                                        fieldWithPath("memberProfileUrl").type(STRING).description("사용자 프로필 이미지 url"),
                                        fieldWithPath("nickname").type(STRING).description("사용자 닉네임"),
                                        fieldWithPath("memberRole").type(STRING).description("사용자 role")

                                )
                                .build()
                        )));
    }

    @DisplayName("최고 관리자는 user로 지정할 수 있다.")
    @WithMockCustomUser
    @Test
    void saveToUser() throws Exception {

        mockMvc.perform(
                        post("/api/v4/members/{memberId}/creators", 1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("최고 관리자는 user로 role 변경",
                        pathParameters(
                                parameterWithName("memberId").description("조회 대상의 memberId")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("관리자 사용자")
                                .summary("관리자가 user로 role 변경")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("memberId").type(NUMBER).description("사용자 id"),
                                        fieldWithPath("memberProfileUrl").type(STRING).description("사용자 프로필 이미지 url"),
                                        fieldWithPath("nickname").type(STRING).description("사용자 닉네임"),
                                        fieldWithPath("memberRole").type(STRING).description("사용자 role")

                                )
                                .build()
                        )));
    }




    @DisplayName("관리자는 사용자의 목록을 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getMembersManager() throws Exception {

        mockMvc.perform(
                        get("/api/v3/members")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("관리자 이벤트 사용자 목록 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("관리자 사용자")
                                .summary("관리자 사용자 목록 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("memberManagerResponses[0].memberId").type(NUMBER).description("사용자 id"),
                                        fieldWithPath("memberManagerResponses[0].memberProfileUrl").type(STRING).description("사용자 프로필 이미지 url"),
                                        fieldWithPath("memberManagerResponses[0].nickname").type(STRING).description("사용자 닉네임"),
                                        fieldWithPath("memberManagerResponses[0].memberRole").type(STRING).description("사용자 role"),
                                        fieldWithPath("memberManagerResponses[0].email").type(STRING).description("사용자 이메일"),
                                        fieldWithPath("memberManagerResponses[0].memberRole").type(STRING).description("사용자 role"),
                                        fieldWithPath("memberManagerResponses[0].loginType").type(STRING).description("사용자 소셜 계정 (KAKAO | GOOGLE)"),
                                        fieldWithPath("memberManagerResponses[0].premiumType").type(STRING).description("사용자 프리미엄 구독 정보 (BASIC | PREMIUM)")
                                )
                                .build()
                        )));
    }

    @DisplayName("관리자는 사용자 수를 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getMembersCountManager() throws Exception {

        mockMvc.perform(
                        get("/api/v3/members/count")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("관리자 사용자 수 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("관리자 사용자")
                                .summary("관리자 사용자 수 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("totalUserCount").type(NUMBER).description(100),
                                        fieldWithPath("totalCreatorCount").type(NUMBER).description(50)
                                )
                                .build()
                        )));
    }

    @DisplayName("관리자는 사용자의 상세 정보를 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getMemberInfoManager() throws Exception {

        mockMvc.perform(
                        get("/api/v3/members/{memberId}/info", 1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("관리자 사용자 상세 정보 조회",
                        pathParameters(
                                parameterWithName("memberId").description("사용자 Id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("관리자 사용자")
                                .summary("관리자 사용자 상세 정보 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("memberId").type(NUMBER).description("사용자 id"),
                                        fieldWithPath("memberProfileUrl").type(STRING).description("사용자 프로필 이미지 url"),
                                        fieldWithPath("nickname").type(STRING).description("사용자 닉네임"),
                                        fieldWithPath("email").type(STRING).description("사용자 이메일"),
                                        fieldWithPath("memberRole").type(STRING).description("사용자 role"),
                                        fieldWithPath("loginType").type(STRING).description("사용자 소셜 계정 (KAKAO | GOOGLE)"),
                                        fieldWithPath("premiumType").type(STRING).description("사용자 프리미엄 구독 정보 (BASIC | PREMIUM)"))
                                .build()
                        )));
    }

    @DisplayName("관리자는 사용자가 팔로우하는 유저의 정보를 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getFollow() throws Exception {

        mockMvc.perform(
                        get("/api/v3/members/{memberId}/follows", 1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("관리자 사용자 팔로우 정보 조회",
                        pathParameters(
                                parameterWithName("memberId").description("사용자 Id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                        .tag("관리자 사용자")
                                        .summary("관리자 사용자 팔로우 정보 조회")
                                        .requestHeaders(
                                                headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                        )
                                        .responseFields(
                                        fieldWithPath("followManagerResponses[0].followMemberProfileUrl").type(STRING).description("사용자가 팔로우하는 타사용자 프로필 이미지 url"),
                                        fieldWithPath("followManagerResponses[0].followMemberNickname").type(STRING).description("사용자가 팔로우하는 타사용자 닉네임")
                                        )
                                        .build()
                        )));
    }

    @DisplayName("관리자는 사용자를 팔로잉하는 유저의 정보를 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getFollowing() throws Exception {

        mockMvc.perform(
                        get("/api/v3/members/{memberId}/followings", 1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("관리자 사용자 팔로잉 정보 조회",
                        pathParameters(
                                parameterWithName("memberId").description("사용자 Id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("관리자 사용자")
                                .summary("관리자 사용자 팔로잉 정보 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("followingManagerResponses[0].followingMemberProfileUrl").type(STRING).description("사용자를 팔로우하는 타사용자 프로필 이미지 Url"),
                                        fieldWithPath("followingManagerResponses[0].followingMemberNickname").type(STRING).description("사용자를 팔로우하는 타사용자 닉네임")
                                )
                                .build()
                        )));
    }

    @DisplayName("관리자는 사용자가 소속된 그룹의 정보를 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getTeam() throws Exception {

        mockMvc.perform(
                        get("/api/v3/members/{memberId}/teams", 1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("관리자 사용자 그룹 정보 조회",
                        pathParameters(
                                parameterWithName("memberId").description("사용자 Id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("관리자 사용자")
                                .summary("관리자 사용자 그룹 정보 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("teamManagerResponses[0].teamProfileUrl").type(STRING).description("사용자가 소속된 그룹 프로필 이미지 url"),
                                        fieldWithPath("teamManagerResponses[0].teamName").type(STRING).description("사용자가 소속된 그룹 이름")
                                )
                                .build()
                        )));
    }

    @DisplayName("관리자는 사용자가 소속된 그룹의 정보를 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getShowcases() throws Exception {

        mockMvc.perform(
                        get("/api/v3/members/{memberId}/showcases", 1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("관리자 사용자 그룹 정보 조회",
                        pathParameters(
                                parameterWithName("memberId").description("사용자 Id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("관리자 사용자")
                                .summary("관리자 사용자 그룹 정보 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("showcaseManagerResponses[0].showcaseIconImage").type(STRING).description("사용자의 진열장 토스트 이미지 url"),
                                        fieldWithPath("showcaseManagerResponses[0].showcaseName").type(STRING).description("사용자의 진열장 토스트 이름")
                                )
                                .build()
                        )));
    }

    @DisplayName("관리자는 사용자가 소속된 그룹의 정보를 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getEventToasts() throws Exception {

        mockMvc.perform(
                        get("/api/v3/members/{memberId}/eventToasts", 1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("관리자 사용자 그룹 정보 조회",
                        pathParameters(
                                parameterWithName("memberId").description("사용자 Id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("관리자 사용자")
                                .summary("관리자 사용자 그룹 정보 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("eventToastManagerResponses[0].eventToastIconImage").type(STRING).description("사용자의 이벤트 토스트 이미지 url"),
                                        fieldWithPath("eventToastManagerResponses[0].eventToastName").type(STRING).description("사용자의 이벤트 토스트 이름")
                                )
                                .build()
                        )));
    }

    @DisplayName("관리자는 사용자가 소속된 그룹의 정보를 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void memberGiftToasts() throws Exception {

        mockMvc.perform(
                        get("/api/v3/members/{memberId}/giftToasts", 1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("관리자 사용자 그룹 정보 조회",
                        pathParameters(
                                parameterWithName("memberId").description("사용자 Id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("관리자 사용자")
                                .summary("관리자 사용자 그룹 정보 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("giftToastManagerResponses[0].giftToastIconImage").type(STRING).description("사용자의 캡슐 토스트 이미지 url"),
                                        fieldWithPath("giftToastManagerResponses[0].giftToastName").type(STRING).description("사용자의 캡슐 토스트 이름")
                                )
                                .build()
                        )));
    }

    @DisplayName("관리자는 사용자가 소속된 그룹의 정보를 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getIconGroups() throws Exception {

        mockMvc.perform(
                        get("/api/v3/members/{memberId}/iconGroups", 1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("관리자 사용자 그룹 정보 조회",
                        pathParameters(
                                parameterWithName("memberId").description("사용자 Id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("관리자 사용자")
                                .summary("관리자 사용자 그룹 정보 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("iconGroupManagerResponses[0].iconGroupName").type(STRING).description("사용자의 아이콘 그룹 이름"),
                                        fieldWithPath("iconGroupManagerResponses[0].iconImages[]").type(ARRAY).description("사용자의 아이콘 그룹 이미지")
                                )
                                .build()
                        )));
    }

    @DisplayName("관리자는 사용자가 소속된 그룹의 정보를 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getPayment() throws Exception {

        mockMvc.perform(
                        get("/api/v3/members/{memberId}/payments", 1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("관리자 사용자 그룹 정보 조회",
                        pathParameters(
                                parameterWithName("memberId").description("사용자 Id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("관리자 사용자")
                                .summary("관리자 사용자 그룹 정보 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("paymentManagerResponses[0].amount").type(NUMBER).description("지불 금액"),
                                        fieldWithPath("paymentManagerResponses[0].paymentState").type(STRING).description("결제 상태 (WAITING | SUCCESS | FAILURE)"),
                                        fieldWithPath("paymentManagerResponses[0].orderId").type(STRING).description("주문 번호"),
                                        fieldWithPath("paymentManagerResponses[0].itemType").type(STRING).description("구매 아이템 타입 (ICON | PREMIUM)"),
                                        fieldWithPath("paymentManagerResponses[0].itemTypeData").type(STRING).description("구매 아이템 정보"),
                                        fieldWithPath("paymentManagerResponses[0].createdAt").type(ARRAY).description("결제 날짜"),
                                        fieldWithPath("paymentManagerResponses[0].nickname").type(STRING).description("결제자 닉네임"),
                                        fieldWithPath("paymentManagerResponses[0].images[]").type(ARRAY).description("관련 이미지"),
                                        fieldWithPath("paymentManagerResponses[0].expiredDate").type(ARRAY).description("만료 날짜")

                                )
                                .build()
                        )));
    }
}
