package com.timeToast.timeToast.global.config;

import com.timeToast.timeToast.global.constant.CorsProperties;
import com.timeToast.timeToast.global.jwt.JwtAccessDeniedHandler;
import com.timeToast.timeToast.global.jwt.JwtAuthenticationEntryPoint;
import com.timeToast.timeToast.global.jwt.JwtFilter;
import com.timeToast.timeToast.global.jwt.JwtTokenProvider;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final CorsProperties corsProperties;

    public SecurityConfig(final JwtTokenProvider jwtTokenProvider, final CorsProperties corsProperties) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.corsProperties = corsProperties;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        return http
                //REST API 이므로, basic auth 및 csrf 보안 사용하지 않음.
                .httpBasic(AbstractHttpConfigurer::disable)
                //cookie 사용 안하면 끔.
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                //h2-console
                .headers(httpSecurityHeaders -> httpSecurityHeaders.frameOptions(frameOptionsConfig -> frameOptionsConfig.sameOrigin()))
                .authorizeHttpRequests(
                        request -> {
                            request.requestMatchers("/api/v4/**").hasAnyRole("MANAGER");

                            request.requestMatchers("/api/v3/login/**").permitAll()
                                    .requestMatchers("/api/v3/**").hasAnyRole("MANAGER", "STAFF");

                            request.requestMatchers("/api/v2/login/**","/api/v2/iconGroups/**").permitAll()
                                    .requestMatchers("/api/v2/**").hasAnyRole("CREATOR");

                            request.requestMatchers("/api/v1/login/**","/api/v1/members/refreshToken").permitAll().
                                    requestMatchers("/api/v1/**").hasAnyRole("MANAGER","STAFF","CREATOR","USER");

                            request.requestMatchers("/h2-console/**", "/actuator/**", "/api/swagger-ui/** ",
                                    "/docs/**", "/v3/api-docs/**", "/swagger-ui/**","/api-docs/**").permitAll();

                            request.anyRequest().authenticated();

                        }
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .addFilterAt((Filter) new JwtFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling( exceptionHandling -> exceptionHandling
                                        .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                                        .accessDeniedHandler(new JwtAccessDeniedHandler())
                )
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                corsProperties.getFrontLocalHost(),
                corsProperties.getBackLocalHost(),
                corsProperties.getServiceDev(),
                corsProperties.getBackDev(),
                corsProperties.getAdminDev(),
                corsProperties.getCreatorDev(),
                corsProperties.getServiceProd(),
                corsProperties.getAdminProd(),
                corsProperties.getCreatorProd()
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
