//package com.timeToast.timeToast.service.oAuth;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.timeToast.timeToast.domain.enums.member.LoginType;
//import com.timeToast.timeToast.dto.member.member.response.LoginResponse;
//import com.timeToast.timeToast.dto.oauth.OAuthResponseDto;
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//import io.jsonwebtoken.impl.Base64UrlCodec;
//import org.springframework.http.HttpHeaders;
//
//import java.nio.charset.StandardCharsets;
//import java.util.Optional;
//
//@Service
//@Transactional
//public class KakaoLoginImpl implements OAuthService {
//
//    private final LoginService loginService;
//
//
//
//    public KakaoLoginImpl(LoginService loginService) {
//        this.loginService = loginService;
//    }
//
//    // for login test
////    public String loadToLogin() {
////        String loginUrl = "https://kauth.kakao.com/oauth/authorize?" + "client_id=" + clientId + "&redirect_uri=" + redirectUrl
////                + "&response_type=code";
////
////        return loginUrl;
////    }
//
//
//
//
//}
