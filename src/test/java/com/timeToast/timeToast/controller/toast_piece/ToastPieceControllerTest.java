package com.timeToast.timeToast.controller.toast_piece;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.timeToast.timeToast.dto.toast_piece.request.ToastPieceRequest;
import com.timeToast.timeToast.service.toast_piece.ToastPieceService;
import com.timeToast.timeToast.service.toast_piece.ToastPieceServiceTest;
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
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ToastPieceControllerTest extends BaseControllerTests {

    private final ToastPieceService toastPieceService = new ToastPieceServiceTest();

    @Override
    protected Object initController() {
        return new ToastPieceController(toastPieceService);
    }


    @DisplayName("토스트 조각 저장")
    @WithMockCustomUser
    @Test
    void toastPieceContents() throws Exception {
        ToastPieceRequest toastPieceRequest = new ToastPieceRequest(1L, 1L, "title");
        String json = objectMapper.writeValueAsString(toastPieceRequest);

        mockMvc.perform(
                        multipart("/api/v1/toastPieces" )
                                .file("toastPieceContents", "hello.png".getBytes())
                                .file("toastPieceImages", "hello.png".getBytes())
                                .file(new MockMultipartFile("toastPieceRequest", "toastPieceRequest", MediaType.APPLICATION_JSON_VALUE, json.getBytes()))
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(status().isOk())
                .andDo(document("토스트 조각 내용 저장",
                        requestParts(
                                partWithName("toastPieceContents").description("토스트 조각 내용 파일"),
                                partWithName("toastPieceImages").description("토스트 조각 이미지 파일 리스트 "),
                                partWithName("toastPieceRequest").description("토스트 조각 요청 정보")
                        ),
                        requestPartBody("toastPieceRequest"), // JSON 필드 문서화
                        requestPartFields("toastPieceRequest",
                                fieldWithPath("giftToastId").type(NUMBER).description("선물 토스 Id"),
                                fieldWithPath("iconId").type(NUMBER).description("팀 Id"),
                                fieldWithPath("title").type(STRING).description("제목")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("토스트 조각")
                                .summary("토스트 조각 내용 저장")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("toastPieceId").type(NUMBER).description("토스트 조각 Id"),
                                        fieldWithPath("giftToastId").type(NUMBER).description("선물 토스트 Id"),
                                        fieldWithPath("iconId").type(NUMBER).description("아이콘 Id"),
                                        fieldWithPath("title").type(STRING).description("제목"),
                                        fieldWithPath("contentsUrl").type(STRING).description("내용 url"),
                                        fieldWithPath("toastPieceImages").type(ARRAY).description("토스트 조각 이미지 list")
                                )
                                .build()
                        )));
    }



    @DisplayName("토스트 조각 단일 상세 조회를 할 수 있다.")
    @WithMockCustomUser
    @Test
    void getToastPieceDetail() throws Exception {


        mockMvc.perform(
                        get("/api/v1/toastPieces/{toastPieceId}", 1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)

                )
                .andExpect(status().isOk())
                .andDo(document("토스트 조각 상세 조회",
                        pathParameters(
                                parameterWithName("toastPieceId").description("토스트 조각 Id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("토스트 조각")
                                .summary("토스트 조각 상세 조회")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .responseFields(
                                        fieldWithPath("memberId").type(NUMBER).description("memberId"),
                                        fieldWithPath("nickname").type(STRING).description("닉네임"),
                                        fieldWithPath("profileUrl").type(STRING).description("프로필 url"),
                                        fieldWithPath("iconImageUrl").type(STRING).description("토스트 조각 이미지 url"),
                                        fieldWithPath("title").type(STRING).description("제목"),
                                        fieldWithPath("contentsUrl").type(STRING).description("토스트 조각 내용 url"),
                                        fieldWithPath("createdAt").type(STRING).description("토스트 조각 생성 일자"),
                                        fieldWithPath("toastPieceImages").type(ARRAY).description("토스트 조각 이미지 리스트")


                                        )
                                .build()
                        )));
    }

    @DisplayName("로그인한 사용자는 자신의 토스트 조각을 삭제할 수 있다.")
    @WithMockCustomUser
    @Test
    void deleteToastPiece() throws Exception {


        mockMvc.perform(
                        delete("/api/v1/toastPieces/{toastPieceId}", 1L)
                                .header(AUTHORIZATION, USER_ACCESS_TOKEN)

                )
                .andExpect(status().isOk())
                .andDo(document("토스트 조각 삭제",
                        pathParameters(
                                parameterWithName("toastPieceId").description("토스트 조각 Id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("토스트 조각")
                                .summary("토스트 조각 삭제")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description(TEST_ACCESS_TOKEN.value())
                                )
                                .build()
                        )));
    }

}