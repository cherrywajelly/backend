package com.timeToast.timeToast.controller.member.member;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.domain.enums.payment.PaymentState;
import com.timeToast.timeToast.domain.enums.premium.PremiumType;
import com.timeToast.timeToast.service.member.member.MemberService;
import com.timeToast.timeToast.service.member.member.MemberServiceTest;
import com.timeToast.timeToast.util.BaseControllerTests;
import com.timeToast.timeToast.util.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.timeToast.timeToast.util.TestConstant.TEST_ACCESS_TOKEN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ManagerMemberControllerTest extends BaseControllerTests {
    private final MemberService memberService = new MemberServiceTest();
    @Override
    protected Object initController() {
        return new ManagerMemberController(memberService);
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
                                        fieldWithPath("memberManagerResponses[0].nickname").type(STRING).description("사용자 닉네임")
                                )
                                .build()
                        )));
    }

    @DisplayName("관리자는 사용자의 상세 정보를 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getMemberInfoManager() throws Exception {

        mockMvc.perform(
                        get("/api/v3/members/{memberId}", 1L)
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
                                        fieldWithPath("loginType").type(STRING).description("사용자 소셜 계정 (KAKAO | GOOGLE)"),
                                        fieldWithPath("premiumType").type(STRING).description("사용자 프리미엄 구독 정보 (BASIC | PREMIUM)"),
                                        fieldWithPath("followManagerResponses[0].followMemberProfileUrl").type(STRING).description("사용자가 팔로우하는 타사용자 프로필 이미지 url"),
                                        fieldWithPath("followManagerResponses[0].followMemberNickname").type(STRING).description("사용자가 팔로우하는 타사용자 닉네임"),
                                        fieldWithPath("followingManagerResponses[0].followingMemberProfileUrl").type(STRING).description("사용자를 팔로우하는 타사용자 프로필 이미지 Url"),
                                        fieldWithPath("followingManagerResponses[0].followingMemberNickname").type(STRING).description("사용자를 팔로우하는 타사용자 닉네임"),
                                        fieldWithPath("teamManagerResponses[0].teamProfileUrl").type(STRING).description("사용자가 소속된 그룹 프로필 이미지 url"),
                                        fieldWithPath("teamManagerResponses[0].teamName").type(STRING).description("사용자가 소속된 그룹 이름"),
                                        fieldWithPath("showCaseManagerResponses[0].showcaseIconImage").type(STRING).description("사용자의 진열장 토스트 이미지 url"),
                                        fieldWithPath("showCaseManagerResponses[0].showcaseName").type(STRING).description("사용자의 진열장 토스트 이름"),
                                        fieldWithPath("eventToastManagerResponses[0].eventToastIconImage").type(STRING).description("사용자의 이벤트 토스트 이미지 url"),
                                        fieldWithPath("eventToastManagerResponses[0].eventToastName").type(STRING).description("사용자의 이벤트 토스트 이름"),
                                        fieldWithPath("giftToastManagerResponses[0].giftToastIconImage").type(STRING).description("사용자의 캡슐 토스트 이미지 url"),
                                        fieldWithPath("giftToastManagerResponses[0].giftToastName").type(STRING).description("사용자의 캡슐 토스트 이름"),
                                        fieldWithPath("iconGroupManagerResponses[0].iconGroupName").type(STRING).description("사용자의 아이콘 그룹 이름"),
                                        fieldWithPath("iconGroupManagerResponses[0].iconImages[]").type(ARRAY).description("사용자의 아이콘 그룹 이미지"),
                                        fieldWithPath("paymentManagerResponse[0].amount").type(NUMBER).description("지불 금액"),
                                        fieldWithPath("paymentManagerResponse[0].paymentState").type(STRING).description("결제 상태 (WAITING | SUCCESS | FAILURE)"),
                                        fieldWithPath("paymentManagerResponse[0].orderId").type(STRING).description("주문 번호"),
                                        fieldWithPath("paymentManagerResponse[0].itemType").type(STRING).description("구매 아이템 타입 (ICON | PREMIUM)"),
                                        fieldWithPath("paymentManagerResponse[0].itemTypeData").type(STRING).description("구매 아이템 정보"),
                                        fieldWithPath("paymentManagerResponse[0].createdAt").type(ARRAY).description("결제 날짜"),
                                        fieldWithPath("paymentManagerResponse[0].nickname").type(STRING).description("결제자 닉네임"),
                                        fieldWithPath("paymentManagerResponse[0].images[]").type(ARRAY).description("관련 이미지"),
                                        fieldWithPath("paymentManagerResponse[0].expiredDate").type(ARRAY).description("만료 날짜")

                                )
                                .build()
                        )));
    }
}
