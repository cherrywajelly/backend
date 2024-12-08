package com.timeToast.timeToast.global.constant;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class CorsProperties {

    @Value("${cors.front-localhost}")
    private String frontLocalHost;

    @Value("${cors.back-localhost}")
    private String backLocalHost;

    @Value("${cors.service-dev}")
    private String serviceDev;

    @Value("${cors.back-dev}")
    private String backDev;

    @Value("${cors.admin-dev}")
    private String adminDev;

    @Value("${cors.creator-dev}")
    private String creatorDev;

    @Value("${cors.service-prod}")
    private String serviceProd;

    @Value("${cors.admin-prod}")
    private String adminProd;

    @Value("${cors.creator-prod}")
    private String creatorProd;
}
