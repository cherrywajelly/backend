package com.timeToast.timeToast.service.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.timeToast.timeToast.dto.oauth.OAuthResponseDto;
import com.timeToast.timeToast.dto.oauth.KakaoUserDataDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import io.jsonwebtoken.impl.Base64UrlCodec;
import org.springframework.http.HttpHeaders;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class KakaoLoginImpl {
    private final String kakaoTokenUrl = "https://kauth.kakao.com/oauth/token";

    @Value("${oauth2.client.kakao.client-id}")
    private String clientId;

    @Value("${oauth2.client.kakao.client-secret}")
    private String clientSecret;

    @Value("${oauth2.client.kakao.redirect-uri}")
    private String redirectUrl;

    // for login test
    public String loadToLogin() {
        String loginUrl = "https://kauth.kakao.com/oauth/authorize?" + "client_id=" + clientId + "&redirect_uri=" + redirectUrl
                + "&response_type=code";

        return loginUrl;
    }

    public String getKakaoAccessToken(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("code", accessToken);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUrl);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        ResponseEntity<OAuthResponseDto> responseEntity = restTemplate.postForEntity(kakaoTokenUrl, requestEntity, OAuthResponseDto.class);
        Optional<KakaoUserDataDto> decodeInfo = decodeToken(responseEntity.getBody().getId_token().split("\\.")[1]);
        return decodeInfo.get().getEmail();
    }

    public Optional<KakaoUserDataDto> decodeToken(String jwtToken) {
        byte[] decode = new Base64UrlCodec().decode(jwtToken);
        String decode_data = new String(decode, StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            KakaoUserDataDto userDataDto = objectMapper.readValue(decode_data, KakaoUserDataDto.class);
            return Optional.ofNullable(userDataDto);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
