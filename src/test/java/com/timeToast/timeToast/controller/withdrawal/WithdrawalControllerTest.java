package com.timeToast.timeToast.controller.withdrawal;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.service.withdrawal.WithdrawalService;
import com.timeToast.timeToast.service.withdrawal.WithdrawalServiceImplTest;
import com.timeToast.timeToast.util.BaseControllerTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.timeToast.timeToast.util.TestConstant.TEST_ACCESS_TOKEN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WithdrawalControllerTest extends BaseControllerTests {

    private final WithdrawalService withdrawalService = new WithdrawalServiceImplTest();
    @Override
    protected Object initController() {
        return new WithdrawalController(withdrawalService);
    }


    @DisplayName("사용자는 탈퇴에 성공한다.")
    @Test()
    void memberWithdrawal() throws Exception {

        mockMvc.perform(
                        delete("/api/v1/withdrawal")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document("사용자 탈퇴 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("탈퇴")
                                .summary("사용자 탈퇴")
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

    @DisplayName("제작자는 탈퇴에 성공한다.")
    @Test()
    void creatorWithdrawal() throws Exception {

        mockMvc.perform(
                        delete("/api/v2/withdrawal")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document("제작자 탈퇴 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("탈퇴")
                                .summary("제작 탈퇴")
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

    @DisplayName("관리자는 탈퇴에 성공한다.")
    @Test()
    void adminWithdrawal() throws Exception {

        mockMvc.perform(
                        delete("/api/v3/withdrawal")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)

                )
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document("관리자 탈퇴 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("탈퇴")
                                .summary("관리자 탈퇴")
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