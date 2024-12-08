package com.timeToast.timeToast.controller.creator;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.service.icon.icon_group.IconGroupAdminService;
import com.timeToast.timeToast.service.icon.icon_group.IconGroupAdminServiceTest;
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
import static java.sql.JDBCType.ARRAY;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreatorAdminControllerTest extends BaseControllerTests {

    private final MemberService memberService = new MemberServiceTest();
    private final IconGroupAdminService iconGroupAdminService = new IconGroupAdminServiceTest();

    @Override
    protected Object initController() {
        return new CreatorAdminController(memberService, iconGroupAdminService);
    }

    @DisplayName("관리자의 제작자 전체 목록 조회 :성공")
    @WithMockCustomUser
    @Test
    void getCreators() throws Exception {
        mockMvc.perform(
                        get("/api/v3/creators")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("관리자의 제작자 전체 목록 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("관리자 - 제작자")
                                .summary("관리자는 제작자 목록을 조회할 수 있다.")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("creatorResponses[0].memberId").type(NUMBER).description("제작자 id 값"),
                                        fieldWithPath("creatorResponses[0].profileUrl").type(STRING).description("제작자 profile url"),
                                        fieldWithPath("creatorResponses[0].nickname").type(STRING).description("제작자 닉네임"),
                                        fieldWithPath("creatorResponses[0].salesIconCount").type(NUMBER).description("판매 아이콘 갯수"),
                                        fieldWithPath("creatorResponses[0].totalRevenue").type(NUMBER).description("전체 수익"),
                                        fieldWithPath("creatorResponses[0].createdIconCount").type(NUMBER).description("제작한 아이콘 갯수")
                                )
                                .build()
                        )));
    }

    @DisplayName("관리자의 제작자 상세 조회")
    @WithMockCustomUser
    @Test
    void getCreatorByCreatorId() throws Exception {
        mockMvc.perform(
                        get("/api/v3/creators/{creatorId}",1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("관리자의 제작자 상세 조회",
                        pathParameters(
                                parameterWithName("creatorId").description("제작자 Id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("관리자 - 제작자")
                                .summary("관리자는 제작자 상세 정보를 조회할 수 있다.")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("profileUrl").type(STRING).description("제작자 프로필 url"),
                                        fieldWithPath("nickname").type(STRING).description("제작자 닉네임"),
                                        fieldWithPath("bank").type(STRING).description("은행"),
                                        fieldWithPath("accountNumber").type(STRING).description("제작자 계좌 번호")
                                )
                                .build()
                        )));
    }

    @DisplayName("관리자의 제작자의 모든 아이콘 그룹 조회")
    @WithMockCustomUser
    @Test
    void getIconGroupsByCreator() throws Exception {
        mockMvc.perform(
                        get("/api/v3/creators/{creatorId}/iconGroups",1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(document("관리자의 제작자의 전체 아이콘 상세 조회",
                        pathParameters(
                                parameterWithName("creatorId").description("제작자 Id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("관리자 - 제작자")
                                .summary("관리자는 제작자의 전체 아이콘을 조회할 수 있다.")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("salesIconCount").type(NUMBER).description("전체 판매 갯수"),
                                        fieldWithPath("totalRevenue").type(NUMBER).description("전체 수익"),
                                        fieldWithPath("createdIconCount").type(NUMBER).description("제작 아이콘 수"),
                                        fieldWithPath("creatorIconInfos[0].title").type(STRING).description("아이콘 그룹 제목"),
                                        fieldWithPath("creatorIconInfos[0].revenue").type(NUMBER).description("아이콘 그룹 수익"),
                                        fieldWithPath("creatorIconInfos[0].salesCount").type(NUMBER).description("아이콘 그룹 판매 갯수"),
                                        fieldWithPath("creatorIconInfos[0].iconImageUrl").type(ARRAY).description("이미지 리스트")
                                )
                                .build()
                        )));
    }

}
