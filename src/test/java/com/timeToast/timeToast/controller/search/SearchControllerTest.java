package com.timeToast.timeToast.controller.search;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.dto.search.request.SearchRequest;
import com.timeToast.timeToast.service.search.SearchService;
import com.timeToast.timeToast.service.search.SearchServiceTest;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SearchControllerTest  extends BaseControllerTests {

    private final SearchService searchService = new SearchServiceTest();
    @Override
    protected Object initController() {
        return new SearchController(searchService);
    }

    @DisplayName("사용자는 키워드를 활용하여 사용자 닉네임을 검색할 수 있다.")
    @WithMockCustomUser
    @Test
    void searchNickname() throws Exception {
        SearchRequest searchRequest = new SearchRequest(0,0,"test");
        String json = objectMapper.writeValueAsString(searchRequest);

        mockMvc.perform(
                        post("/api/v1/search")
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                                .contentType(APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("검색 결과 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("검색")
                                .summary("키워드를 통한 검색 결과 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .requestFields(
                                        fieldWithPath("page").type(NUMBER).description("요청 페이지 번호"),
                                        fieldWithPath("size").type(NUMBER).description("요청 갯수"),
                                        fieldWithPath("searchKeyword").type(STRING).description("검색어")
                                )
                                .responseFields(
                                        fieldWithPath("nextPage").type(NUMBER).description("다음 페이지"),
                                        fieldWithPath("size").type(NUMBER).description("요청 갯수"),
                                        fieldWithPath("searchResponses[0].memberId").type(NUMBER).description("사용자 id"),
                                        fieldWithPath("searchResponses[0].nickname").type(STRING).description("사용자 닉네임"),
                                        fieldWithPath("searchResponses[0].profileUrl").type(STRING).description("사용자 프로필 url")
                                        )
                                .build()

                        )));
    }
}