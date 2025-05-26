//package com.timeToast.timeToast.controller.creator;
//
//import com.epages.restdocs.apispec.ResourceSnippetParameters;
//import com.timeToast.timeToast.controller.member.member.CreatorAdminController;
//import com.timeToast.timeToast.service.icon.icon_group.IconGroupAdminService;
//import com.timeToast.timeToast.service.icon.icon_group.IconGroupAdminServiceTest;
//import com.timeToast.timeToast.service.member.member.MemberService;
//import com.timeToast.timeToast.service.member.member.MemberServiceTest;
//import com.timeToast.timeToast.util.BaseControllerTests;
//import com.timeToast.timeToast.util.WithMockCustomUser;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
//import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
//import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
//import static com.timeToast.timeToast.util.TestConstant.TEST_ACCESS_TOKEN;
//import static java.sql.JDBCType.ARRAY;
//import static org.springframework.http.HttpHeaders.AUTHORIZATION;
//import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
//import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
//import static org.springframework.restdocs.payload.JsonFieldType.STRING;
//import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
//import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
//import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//public class CreatorAdminControllerTest extends BaseControllerTests {
//
//    private final MemberService memberService = new MemberServiceTest();
//    private final IconGroupAdminService iconGroupAdminService = new IconGroupAdminServiceTest();
//
//    @Override
//    protected Object initController() {
//        return new CreatorAdminController(memberService, iconGroupAdminService);
//    }
//
//
//
//}
