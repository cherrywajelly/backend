package com.timeToast.timeToast.controller.member.member;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.service.member.member.MemberService;
import com.timeToast.timeToast.service.member.member.MemberServiceTest;
import com.timeToast.timeToast.util.BaseControllerTests;
import com.timeToast.timeToast.util.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.timeToast.timeToast.util.TestConstant.TEST_ACCESS_TOKEN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AppMemberControllerTest extends BaseControllerTests {

    private final MemberService memberService = new MemberServiceTest();

    @Override
    protected Object initController() {
        return new AppMemberController(memberService);
    }

    @DisplayName("로그인한 사용자의 프로필을 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getMemberProfileInfoByLogin() throws Exception {

        mockMvc.perform(
                        get("/api/v1/members")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("로그인한 사용자의 프로필 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("[앱] 멤버")
                                .summary("로그인한 사용자의 프로필 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("nickname").type(STRING).description("닉네임"),
                                        fieldWithPath("profileUrl").type(STRING).description("프로필 url"),
                                        fieldWithPath("isFollow").type(BOOLEAN).description("팔로우 여부")
                                )
                                .build()
                        )));
    }

    @DisplayName("사용자의 프로필을 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getProfileInfo() throws Exception {

        mockMvc.perform(
                        get("/api/v1/members/{memberId}",1)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("사용자의 프로필 조회",
                        pathParameters(
                                parameterWithName("memberId").description("조회 대상의 memberId")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("[앱] 멤버")
                                .summary("사용자의 프로필 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("nickname").type(STRING).description("닉네임"),
                                        fieldWithPath("profileUrl").type(STRING).description("프로필 url"),
                                        fieldWithPath("isFollow").type(BOOLEAN).description("팔로우 여부")
                                )
                                .build()
                        )));
    }

    @DisplayName("로그인한 사용자의 닉네임과 프로필 사진을 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getMemberInfoByLogin() throws Exception {

        mockMvc.perform(
                        get("/api/v1/members/info")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("로그인한 사용자의 닉네임, 프로필 사진 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("[앱] 멤버")
                                .summary("닉네임과 프로필 사진을 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("memberId").type(NUMBER).description("member iconGroupId"),
                                        fieldWithPath("nickname").type(STRING).description("닉네임"),
                                        fieldWithPath("profileUrl").type(STRING).description("프로필 url"),
                                        fieldWithPath("email").type(STRING).description("이메일"),
                                        fieldWithPath("memberRole").type(STRING).description("역할"),
                                        fieldWithPath("loginType").type(STRING).description("로그인 타압"),
                                        fieldWithPath("memberPremium.premiumId").type(NUMBER).description("프리미엄 iconGroupId"),
                                        fieldWithPath("memberPremium.premiumType").type(STRING).description("프리미엄 종류"),
                                        fieldWithPath("memberPremium.expiredDate").type(STRING).description("프리미엄 만료일자")

                                )
                                .build()
                        )));
    }

    @DisplayName("사용자의 닉네임과 프로필 사진을 조회할 수 있다.")
    @WithMockCustomUser
    @Test
    void getMemberInfo() throws Exception {

        mockMvc.perform(
                        get("/api/v1/members/{memberId}/info",1)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("사용자의 닉네임, 프로필 사진 조회",
                        pathParameters(
                                parameterWithName("memberId").description("조회 대상의 memberId")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("[앱] 멤버")
                                .summary("닉네임과 프로필 사진을 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("memberId").type(NUMBER).description("member iconGroupId"),
                                        fieldWithPath("nickname").type(STRING).description("닉네임"),
                                        fieldWithPath("profileUrl").type(STRING).description("프로필 url"),
                                        fieldWithPath("email").type(STRING).description("이메일"),
                                        fieldWithPath("memberRole").type(STRING).description("역할"),
                                        fieldWithPath("loginType").type(STRING).description("로그인 타입"),
                                        fieldWithPath("memberPremium.premiumId").type(NUMBER).description("프리미엄 iconGroupId"),
                                        fieldWithPath("memberPremium.premiumType").type(STRING).description("프리미엄 종류"),
                                        fieldWithPath("memberPremium.expiredDate").type(STRING).description("프리미엄 만료일자")
                                )
                                .build()
                        )));
    }


}