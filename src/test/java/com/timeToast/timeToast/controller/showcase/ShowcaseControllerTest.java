package com.timeToast.timeToast.controller.showcase;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.dto.gift_toast.request.GiftToastGroupRequest;
import com.timeToast.timeToast.dto.showcase.request.ShowcaseSaveRequest;
import com.timeToast.timeToast.service.showcase.ShowcaseService;
import com.timeToast.timeToast.service.showcase.ShowcaseServiceTest;
import com.timeToast.timeToast.util.BaseControllerTests;
import com.timeToast.timeToast.util.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.timeToast.timeToast.util.TestConstant.TEST_ACCESS_TOKEN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
public class ShowcaseControllerTest extends BaseControllerTests {

    private final ShowcaseService showcaseService = new ShowcaseServiceTest();

    @Override
    protected Object initController() {
        return new ShowcaseController(showcaseService);
    }

    @DisplayName("showcase를 등록할 수 있다.")
    @WithMockCustomUser
    @Test
    void saveGiftToastGroup() throws Exception {

        ShowcaseSaveRequest showcaseSaveRequest = new ShowcaseSaveRequest(List.of(1L));
        String json = objectMapper.writeValueAsString(showcaseSaveRequest);

        mockMvc.perform(
                        post("/api/v1/showcases")
                                .header(AUTHORIZATION, TEST_ACCESS_TOKEN.value())
                                .contentType(APPLICATION_JSON)
                                .content(json)

                )
                .andExpect(status().isOk())
                .andDo(document("진열장 등록",
                        resource(ResourceSnippetParameters.builder()
                                .tag("진열장")
                                .summary("진열장 등록")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .requestFields(
                                        fieldWithPath("showcases[0]").type(ARRAY).description("등록 eventToast Id")
                                )
                                .responseFields(
                                        fieldWithPath("showcaseSaveResponses[0]").type(ARRAY).description("등록한 event toast title")
                                )
                                .build()
                        )));
    }

    @DisplayName("showcase 등록할 이벤트 토스트 리스트 조회")
    @WithMockCustomUser
    @Test
    void getShowcaseSaveList() throws Exception {


        mockMvc.perform(
                        get("/api/v1/showcases")
                                .header(AUTHORIZATION, TEST_ACCESS_TOKEN.value())

                )
                .andExpect(status().isOk())
                .andDo(document("진열장 등록 가능한 토스트 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("진열장")
                                .summary("진열장 등록 가능한 토스트 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("showcaseEditResponses[0].eventToastId").type(NUMBER).description("이벤트 토스트 id"),
                                        fieldWithPath("showcaseEditResponses[0].iconUrl").type(STRING).description("아이콘 url"),
                                        fieldWithPath("showcaseEditResponses[0].title").type(STRING).description("제목"),
                                        fieldWithPath("showcaseEditResponses[0].openedDate").type(STRING).description("오픈 날짜"),
                                        fieldWithPath("showcaseEditResponses[0].isShowcase").type(BOOLEAN).description("진열장 등록 여부"),
                                        fieldWithPath("showcaseEditResponses[0].showCaseId").type(NUMBER).description("진열장 등록시 id")
                                )
                                .build()
                        )));
    }

    @DisplayName("로그인한 회원의 showcase 조회")
    @WithMockCustomUser
    @Test
    void getShowcaseByLogin() throws Exception {


        mockMvc.perform(
                        get("/api/v1/showcases/members")
                                .header(AUTHORIZATION, TEST_ACCESS_TOKEN.value())

                )
                .andExpect(status().isOk())
                .andDo(document("로그인한 회원의 진열장 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("진열장")
                                .summary("로그인한 회원의 진열장 조")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("showcaseResponses[0].eventToastId").type(NUMBER).description("이벤트 토스트 id"),
                                        fieldWithPath("showcaseResponses[0].iconUrl").type(STRING).description("아이콘 url")
                                )
                                .build()
                        )));
    }

    @DisplayName("showcase 조회")
    @WithMockCustomUser
    @Test
    void getShowcase() throws Exception {


        mockMvc.perform(
                        get("/api/v1/showcases/members/{memberId}", 1L)
                                .header(AUTHORIZATION, TEST_ACCESS_TOKEN.value())

                )
                .andExpect(status().isOk())
                .andDo(document("진열장 조회",
                        pathParameters(
                                parameterWithName("memberId").description("memberId")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("진열장")
                                .summary("진열장 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("showcaseResponses[0].eventToastId").type(NUMBER).description("이벤트 토스트 id"),
                                        fieldWithPath("showcaseResponses[0].iconUrl").type(STRING).description("아이콘 url")
                                )
                                .build()
                        )));
    }

    @DisplayName("showcase 조회")
    @WithMockCustomUser
    @Test
    void deleteShowcase() throws Exception {


        mockMvc.perform(
                        delete("/api/v1/showcases/{showcaseId}", 1L)
                                .header(AUTHORIZATION, TEST_ACCESS_TOKEN.value())

                )
                .andExpect(status().isOk())
                .andDo(document("진열장 조회",
                        pathParameters(
                                parameterWithName("showcaseId").description("showcase id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("진열장")
                                .summary("진열장 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .build()
                        )));
    }
}