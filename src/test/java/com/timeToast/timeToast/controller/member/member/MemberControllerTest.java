package com.timeToast.timeToast.controller.member.member;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.service.jwt.JwtService;
import com.timeToast.timeToast.service.jwt.JwtServiceTest;
import com.timeToast.timeToast.service.member.member.MemberService;
import com.timeToast.timeToast.service.member.member.MemberServiceTest;
import com.timeToast.timeToast.util.BaseControllerTests;
import com.timeToast.timeToast.util.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

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

public class MemberControllerTest extends BaseControllerTests {
    private final MemberService memberService = new MemberServiceTest();
    private JwtService jwtService = new JwtServiceTest();

    @Override
    protected Object initController() {
        return new MemberController(memberService, jwtService);
    }

    @DisplayName("사용자는 리프레쉬 토큰으로 토큰 갱신을 할 수 있다. ")
    @WithMockCustomUser
    @Test
    void tokenRenewal() throws Exception {

        mockMvc.perform(
                        post("/api/v1/members/refreshToken")
                                .param("refreshToken", USER_ACCESS_TOKEN)

                )
                .andExpect(status().isOk())
                .andDo(document("리프레쉬 토큰 갱신",
                        resource(ResourceSnippetParameters.builder()
                                .tag("유저 - 멤버")
                                .summary("리프레쉬 토큰 갱신")
                                .responseFields(
                                        fieldWithPath("accessToken").type(STRING).description("access token"),
                                        fieldWithPath("refreshToken").type(STRING).description("access token"),
                                        fieldWithPath("isNew").type(BOOLEAN).description("신규 가입 여부")

                                )
                                .build()
                        )));
    }

    @DisplayName("로그인한 사용자의 프로필 사진을 변경할 수 있다.")
    @WithMockCustomUser
    @Test
    void saveProfileImage() throws Exception {

        mockMvc.perform(
                        multipart("/api/v1/members/profile-image")
                                .file("profileImage", "hello.png".getBytes())
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(status().isOk())
                .andDo(document("프로필 사진 저장",
                        requestParts(
                                partWithName("profileImage").description("프로필 이미지")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("유저 - 멤버")
                                .summary("사용자의 프로필 사진 변경")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("memberId").type(NUMBER).description("member id"),
                                        fieldWithPath("nickname").type(STRING).description("닉네임"),
                                        fieldWithPath("profileUrl").type(STRING).description("프로필 url"),
                                        fieldWithPath("email").type(STRING).description("이메일"),
                                        fieldWithPath("memberRole").type(STRING).description("역할"),
                                        fieldWithPath("loginType").type(STRING).description("로그인 타입"),
                                        fieldWithPath("memberPremium.premiumId").type(NUMBER).description("프리미엄 id"),
                                        fieldWithPath("memberPremium.premiumType").type(STRING).description("프리미엄 종류"),
                                        fieldWithPath("memberPremium.expiredDate").type(STRING).description("프리미엄 만료일자")
                                )
                                .build()
                        )));
    }

    @DisplayName("사용자는 닉네임 중복을 확인할 수 있다.")
    @WithMockCustomUser
    @Test
    void isNicknameAvailable() throws Exception {

        mockMvc.perform(
                        get("/api/v1/members/nickname-validation")
                                .param("nickname", "nickname")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("닉네임 중복 확인",
                        resource(ResourceSnippetParameters.builder()
                                .tag("유저 - 멤버")
                                .summary("닉네임 중복 확인")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .queryParameters(
                                        parameterWithName("nickname").description("닉네임")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("메시지")
                                )
                                .build()
                        )));
    }

    @DisplayName("사용자는 닉네임 중복을 확인할 수 있다. - 실패: 중복")
    @WithMockCustomUser
    @Test
    void isNicknameAvailableConflict() throws Exception {

        mockMvc.perform(
                        get("/api/v1/members/nickname-validation")
                                .param("nickname", "conflictNickname")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isConflict())
                .andDo(document("닉네임 중복 확인",
                        resource(ResourceSnippetParameters.builder()
                                .tag("유저 - 멤버")
                                .summary("닉네임 중복 확인")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .queryParameters(
                                        parameterWithName("nickname").description("닉네임")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("이미 존재하는 닉네임입니다.")
                                )
                                .build()
                        )));
    }

    @DisplayName("사용자는 닉네임을 변경할 수 있다.")
    @WithMockCustomUser
    @Test
    void saveNickname() throws Exception {

        mockMvc.perform(
                        put("/api/v1/members")
                                .param("nickname", "nickname")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)

                )
                .andExpect(status().isOk())
                .andDo(document("로그인한 사용자의 닉네임 변경",
                        resource(ResourceSnippetParameters.builder()
                                .tag("유저 - 멤버")
                                .summary("로그인한 사용자의 닉네임 변경")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("memberId").type(NUMBER).description("member id"),
                                        fieldWithPath("nickname").type(STRING).description("닉네임"),
                                        fieldWithPath("profileUrl").type(STRING).description("프로필 url"),
                                        fieldWithPath("email").type(STRING).description("이메일"),
                                        fieldWithPath("memberRole").type(STRING).description("역할"),
                                        fieldWithPath("loginType").type(STRING).description("로그인 타입"),
                                        fieldWithPath("memberPremium.premiumId").type(NUMBER).description("프리미엄 id"),
                                        fieldWithPath("memberPremium.premiumType").type(STRING).description("프리미엄 종류"),
                                        fieldWithPath("memberPremium.expiredDate").type(STRING).description("프리미엄 만료일자")
                                )
                                .build()
                        )));
    }

    @DisplayName("사용자는 닉네임을 변경할 수 있다. - 실패: 중복")
    @WithMockCustomUser
    @Test
    void saveNicknameConflict() throws Exception {

        mockMvc.perform(
                        put("/api/v1/members")
                                .param("nickname", "conflictNickname")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)

                )
                .andExpect(status().isConflict())
                .andDo(document("로그인한 사용자의 닉네임 변경",
                        resource(ResourceSnippetParameters.builder()
                                .tag("유저 - 멤버")
                                .summary("로그인한 사용자의 닉네임 변경")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("이미 존재하는 닉네임입니다.")
                                )
                                .build()
                        )));
    }

}
