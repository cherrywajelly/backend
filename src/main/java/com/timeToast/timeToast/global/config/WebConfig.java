package com.timeToast.timeToast.global.config;

import com.timeToast.timeToast.global.constant.CorsProperties;
import com.timeToast.timeToast.global.resolver.LoginMemberResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginMemberResolver loginMemberResolver;
    private final CorsProperties corsProperties;

    public WebConfig(final LoginMemberResolver loginMemberResolver, final CorsProperties corsProperties) {
        this.loginMemberResolver = loginMemberResolver;
        this.corsProperties = corsProperties;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOriginPatterns(
                        corsProperties.getDev(),
                        corsProperties.getLocal(),
                        corsProperties.getServiceDev(),
                        corsProperties.getServiceProd(),
                        corsProperties.getAdminDev(),
                        corsProperties.getAdminProd(),
                        corsProperties.getCreatorDev(),
                        corsProperties.getCreatorProd()
                )
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .exposedHeaders("*");

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginMemberResolver);
    }


}
