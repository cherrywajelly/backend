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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
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
}
