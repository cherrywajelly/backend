package com.timeToast.timeToast.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timeToast.timeToast.dto.OAuthResponseDto;
import com.timeToast.timeToast.dto.KakaoUserDataDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import io.jsonwebtoken.impl.Base64UrlCodec;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class KakaoLoginServiceImpl {
    private final String kakaoTokenUrl = "https://kauth.kakao.com/oauth/authorize";


    @Value("${oauth2.client.kakao.client-id}")
    private String clientId;

    @Value("${oauth2.client.kakao.client-secret}")
    private String clientSecret;

    @Value("${oauth2.client.kakao.redirect-uri}")
    private String redirectUrl;

    public String getKakaoAccessToken(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();

        params.put("code", accessToken);
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("redirect_uri", redirectUrl);
        params.put("grant_type", "authorization_code");
        ResponseEntity<OAuthResponseDto> responseEntity = restTemplate.postForEntity(kakaoTokenUrl, params, OAuthResponseDto.class);
        Optional<KakaoUserDataDto> decodeInfo = decodeToken(responseEntity.getBody().getId_token().split("\\.")[1]);

        return loginToService(decodeInfo.get().getEmail());
    }

    public Optional<KakaoUserDataDto> decodeToken(String jwtToken) {
        byte[] decode = new Base64UrlCodec().decode(jwtToken);
        String decode_data = new String(decode, StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            KakaoUserDataDto userDataDto = objectMapper.readValue(decode_data, KakaoUserDataDto.class);
            return Optional.ofNullable(userDataDto);
        }
        catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String loginToService(String email) {
        return "곧 개발 예정 ,,";
    }
}
