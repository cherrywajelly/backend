package com.timeToast.timeToast.controller.member.member;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.domain.enums.member.Bank;
import com.timeToast.timeToast.dto.member.member.request.CreatorAccount;
import com.timeToast.timeToast.service.member.member.MemberService;
import com.timeToast.timeToast.service.member.member.MemberServiceTest;
import com.timeToast.timeToast.util.BaseControllerTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.timeToast.timeToast.util.TestConstant.TEST_ACCESS_TOKEN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FactoryMemberControllerTest extends BaseControllerTests {
    private final MemberService memberService = new MemberServiceTest();

    @Override
    protected Object initController() {
        return new FactoryMemberController(memberService);
    }


    @DisplayName("아이콘 제작자는 회원가입 시, 자신의 정보를 등록할 수 있다.")
    @Test
    void saveCreatorInfo() throws Exception {
        CreatorAccount creatorAccount = new CreatorAccount(Bank.HANA, "accountNumber");
        String json = objectMapper.writeValueAsString(creatorAccount);

        mockMvc.perform(
                        post("/api/v2/members")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                                .contentType(APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("아이콘 제작자 정보 등록",
                        resource(ResourceSnippetParameters.builder()
                                .tag("제작자 - 멤버")
                                .summary("정보 등록")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .requestFields(
                                        fieldWithPath("bank").type(STRING).description("아이콘 제작자 은행"),
                                        fieldWithPath("accountNumber").type(STRING).description("아이콘 제작자 계좌번호")
                                )
                                .responseFields(
                                        fieldWithPath("nickname").type(STRING).description("아이콘 제작자 닉네임"),
                                        fieldWithPath("bank").type(STRING).description("아이콘 제작자 은행"),
                                        fieldWithPath("accountNumber").type(STRING).description("아이콘 제작자 계좌번호"),
                                        fieldWithPath("profileUrl").type(STRING).description("아이콘 제작자 프로필 사진")
                                )
                                .build()
                        )));
    }


}
