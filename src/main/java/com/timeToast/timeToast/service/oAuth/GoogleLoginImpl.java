package com.timeToast.timeToast.service.oAuth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.timeToast.timeToast.dto.oauth.GoogleUserDataDto;
import com.timeToast.timeToast.dto.oauth.OAuthResponseDto;
import io.jsonwebtoken.impl.Base64UrlCodec;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class GoogleLoginImpl  implements OAuthService {

    private final String googleTokenUrl = "https://oauth2.googleapis.com/token";

    @Value("${oauth2.client.google.client-id}")
    private String clientId;

    @Value("${oauth2.client.google.client-secret}")
    private String clientSecret;

    @Value("${oauth2.client.google.redirect-uri}")
    private String redirectUrl;

    // for login test
    public String loadToLogin() {
        String loginUrl = "https://accounts.google.com/o/oauth2/v2/auth?" + "client_id=" + clientId + "&redirect_uri=" + redirectUrl
                + "&response_type=code&scope=email&access_type=offline";

        return loginUrl;
    }

    public String getAccessToken(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();

        params.put("code", accessToken);
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("redirect_uri", redirectUrl);
        params.put("grant_type", "authorization_code");

        ResponseEntity<OAuthResponseDto> responseEntity = restTemplate.postForEntity(googleTokenUrl, params, OAuthResponseDto.class);
        Optional<GoogleUserDataDto> decodeInfo = decodeToken(responseEntity.getBody().getId_token().split("\\.")[1]);
        return decodeInfo.get().getEmail();
    }

    public Optional<GoogleUserDataDto> decodeToken(String jwtToken) {
        byte[] decode = new Base64UrlCodec().decode(jwtToken);
        String decode_data = new String(decode, StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            GoogleUserDataDto userDataDto = objectMapper.readValue(decode_data, GoogleUserDataDto.class);
            return Optional.ofNullable(userDataDto);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
