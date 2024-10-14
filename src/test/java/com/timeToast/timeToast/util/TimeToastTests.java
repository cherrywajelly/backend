package com.timeToast.timeToast.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

@ExtendWith(RestDocumentationExtension.class)
public class TimeToastTests {


    protected MockMvc mockMvc;

    @BeforeEach
    void setUp(final RestDocumentationContextProvider provider) {
//        this.mockMvc = MockMvcBuilders.standaloneSetup(initController())
//                .setControllerAdvice(new ApiRestControllerAdvice(new RestTemplate()))
//                .setCustomArgumentResolvers(new LoginArgumentResolver())
//                .addInterceptors(new FakeInterceptor(objectMapper))
//                .apply(documentationConfiguration(provider))
//                .build();
    }

    protected abstract Object initController();
}
