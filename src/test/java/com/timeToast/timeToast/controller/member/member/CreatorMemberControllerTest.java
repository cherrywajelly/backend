package com.timeToast.timeToast.controller.member.member;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.controller.jam.JamController;
import com.timeToast.timeToast.domain.enums.creator_account.Bank;
import com.timeToast.timeToast.dto.creator_account.response.CreatorAccountResponse;
import com.timeToast.timeToast.dto.jam.request.JamRequest;
import com.timeToast.timeToast.dto.member.member.request.CreatorRequest;
import com.timeToast.timeToast.service.member.member.CreatorService;
import com.timeToast.timeToast.service.member.member.CreatorServiceTest;
import com.timeToast.timeToast.service.member.member.MemberService;
import com.timeToast.timeToast.service.member.member.MemberServiceTest;
import com.timeToast.timeToast.util.BaseControllerTests;
import com.timeToast.timeToast.util.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.timeToast.timeToast.util.TestConstant.TEST_ACCESS_TOKEN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreatorMemberControllerTest extends BaseControllerTests {
    private final CreatorService creatorService = new CreatorServiceTest();
    private final MemberService memberService = new MemberServiceTest();

    @Override
    protected Object initController() {
        return new CreatorMemberController(creatorService, memberService);
    }

    @DisplayName("아이콘 제작자는 닉네임의 중복 여부를 조회할 수 있다.")
    @Test
    void isNicknameAvailable() throws Exception {

        mockMvc.perform(
                        get("/api/v2/members/nickname-validation")
                                .param("nickname", "nickname")
                                .header(AUTHORIZATION, TEST_ACCESS_TOKEN.value())
                )
                .andExpect(status().isOk())
                .andDo(document("아이콘 제작자 닉네임 중복 검증",
                        resource(ResourceSnippetParameters.builder()
                                .tag("아이콘 제작자")
                                .summary("닉네임 중복 검증")
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

    @DisplayName("아이콘 제작자는 회원가입 시, 자신의 정보를 등록할 수 있다.")
    @Test
    void saveCreatorInfo() throws Exception {
        CreatorAccountResponse creatorAccountResponse = new CreatorAccountResponse(Bank.HANA, "accountNumber");
        CreatorRequest creatorRequest = new CreatorRequest("nickname", creatorAccountResponse);
        String json = objectMapper.writeValueAsString(creatorRequest);

        mockMvc.perform(
                        multipart("/api/v2/members/creator-info")
                                .file("profile", "profile.png".getBytes())
                                .file(new MockMultipartFile("creatorRequest", "creatorRequest", MediaType.APPLICATION_JSON_VALUE, json.getBytes()))
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(status().isOk())
                .andDo(document("아이콘 제작자 정보 등록",
                        requestParts(
                                partWithName("profile").description("아이콘 제작자 프로필 이미지"),
                                partWithName("creatorRequest").description("아이콘 제작자 정보")
                        ),
                        requestPartBody("creatorRequest"),
                        requestPartFields("creatorRequest",
                                fieldWithPath("nickname").type(STRING).description("아이콘 제작자 닉네임"),
                                fieldWithPath("creatorAccountResponse.bank").type(STRING).description("아이콘 제작자 은행"),
                                fieldWithPath("creatorAccountResponse.accountNumber").type(STRING).description("아이콘 제작자 계좌번호")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("아이콘 제작자")
                                .summary("정보 등록")
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

    @DisplayName("아이콘 제작자는 자신의 프로필을 조회할 수 있다.")
    @Test
    void getCreatorProfile() throws Exception {

        mockMvc.perform(
                        get("/api/v2/members")
                                .header(AUTHORIZATION, TEST_ACCESS_TOKEN.value())
                )
                .andExpect(status().isOk())
                .andDo(document("아이콘 제작자 프로필 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("아이콘 제작자")
                                .summary("프로필 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("creatorInfoResponse.nickname").type(STRING).description("아이콘 제작자 닉네임"),
                                        fieldWithPath("creatorInfoResponse.bank").type(STRING).description("아이콘 제작자 은행"),
                                        fieldWithPath("creatorInfoResponse.accountNumber").type(STRING).description("아이콘 제작자 계좌번호"),
                                        fieldWithPath("creatorInfoResponse.profileUrl").type(STRING).description("아이콘 제작자 프로필 이미지"),
                                        fieldWithPath("iconGroupOrderedResponses.iconGroupOrderedResponses[0].iconName").type(STRING).description("아이콘 이름"),
                                        fieldWithPath("iconGroupOrderedResponses.iconGroupOrderedResponses[0].thumbnailImageUrl").type(STRING).description("아이콘 대표 이미지"),
                                        fieldWithPath("iconGroupOrderedResponses.iconGroupOrderedResponses[0].iconImageUrl[]").type(ARRAY).description("아이콘 이미지 url"),
                                        fieldWithPath("iconGroupOrderedResponses.iconGroupOrderedResponses[0].orderCount").type(NUMBER).description("아이콘 판매 수량"),
                                        fieldWithPath("iconGroupOrderedResponses.iconGroupOrderedResponses[0].income").type(NUMBER).description("아이콘 판매 수익"),
                                        fieldWithPath("iconGroupOrderedResponses.iconGroupOrderedResponses[0].iconState").type(STRING).description("아이콘 등록 상태"),
                                        fieldWithPath("createdIconCount").type(NUMBER).description("제작한 아이콘 개수"),
                                        fieldWithPath("selledIconCount").type(NUMBER).description("판매한 아이콘 개수"),
                                        fieldWithPath("revenue").type(NUMBER).description("판매 수익"),
                                        fieldWithPath("settlement").type(NUMBER).description("정산 금액")
                                )
                                .build()
                        )));
    }

    @DisplayName("아이콘 제작자는 자신의 정보를 수정할 수 있다.")
    @Test
    void putCreatorInfo() throws Exception {
        CreatorAccountResponse creatorAccountResponse = new CreatorAccountResponse(Bank.HANA, "accountNumber");
        CreatorRequest creatorRequest = new CreatorRequest("nickname", creatorAccountResponse);
        String json = objectMapper.writeValueAsString(creatorRequest);

        mockMvc.perform(
                        multipart("/api/v2/members")
                                .file("profile", "profile.png".getBytes())
                                .file(new MockMultipartFile("creatorRequest", "creatorRequest", MediaType.APPLICATION_JSON_VALUE, json.getBytes()))
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                                .with(request -> {
                                    request.setMethod("PUT");
                                    return request;
                                })
                )
                .andExpect(status().isOk())
                .andDo(document("아이콘 제작자 정보 수정",
                        requestParts(
                                partWithName("profile").description("아이콘 제작자 프로필 이미지"),
                                partWithName("creatorRequest").description("아이콘 제작자 정보")
                        ),
                        requestPartBody("creatorRequest"),
                        requestPartFields("creatorRequest",
                                fieldWithPath("nickname").type(STRING).description("아이콘 제작자 닉네임"),
                                fieldWithPath("creatorAccountResponse.bank").type(STRING).description("아이콘 제작자 은행"),
                                fieldWithPath("creatorAccountResponse.accountNumber").type(STRING).description("아이콘 제작자 계좌번호")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("아이콘 제작자")
                                .summary("정보 수정")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("nickname").type(STRING).description("아이콘 제작자 닉네임"),
                                        fieldWithPath("bank").type(STRING).description("아이콘 제작자 은행"),
                                        fieldWithPath("accountNumber").type(STRING).description("아이콘 제작자 계좌번호"),
                                        fieldWithPath("profileUrl").type(STRING).description("아이콘 제작자 프로필 이미지")
                                )
                                .build()
                        )));
    }
}
