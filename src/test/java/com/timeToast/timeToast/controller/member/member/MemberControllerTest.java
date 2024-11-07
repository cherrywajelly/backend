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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberControllerTest extends BaseControllerTests {

    private final MemberService memberService = new MemberServiceTest();
    private final JwtService jwtService = new JwtServiceTest();

    @Override
    protected Object initController() {
        return new MemberController(memberService, jwtService);
    }
    @DisplayName("로그인한 사용자의 프로필 사진을 변경할 수 있다.")
    @WithMockCustomUser
    @Test
    void createFollow() throws Exception {

        mockMvc.perform(
                        multipart("/api/v1/members/profile-image")
                                .file("profileImage", "hello.png".getBytes())
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                                .header(AUTHORIZATION, TEST_ACCESS_TOKEN.value())
                )
                .andExpect(status().isOk())
                .andDo(document("프로필 사진 저장",
                        pathParameters(
                                parameterWithName("followingId").description("팔로워 대상의 memberId")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("멤버")
                                .summary("로그인한 사용자의 프로필 사진 변경")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .build()
                        )));
    }


}