package com.timeToast.timeToast.service.oAuth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.dto.member.member.LoginResponse;
import com.timeToast.timeToast.dto.member.oauth.GoogleUserDataDto;
import com.timeToast.timeToast.dto.member.oauth.KakaoUserDataDto;
import com.timeToast.timeToast.dto.member.oauth.OAuthResponseDto;
import io.jsonwebtoken.impl.Base64UrlCodec;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class OAuthServiceImpl implements OAuthService {

    private final LoginService loginService;

    private final String googleTokenUrl = "https://oauth2.googleapis.com/token";

    @Value("${oauth2.client.google.client-id}")
    private String googleClientId;

    @Value("${oauth2.client.google.client-secret}")
    private String googleClientSecret;

    @Value("${oauth2.client.google.redirect-uri}")
    private String googleRedirectUrl;

    private final String kakaoTokenUrl = "https://kauth.kakao.com/oauth/token";

    @Value("${oauth2.client.kakao.client-id}")
    private String kakaoClientId;

    @Value("${oauth2.client.kakao.client-secret}")
    private String kakaoClientSecret;

    @Value("${oauth2.client.kakao.redirect-uri}")
    private String kakaoRedirectUrl;

    public OAuthServiceImpl(final LoginService loginService) {
        this.loginService = loginService;
    }

    // for login test
    public String loadToLogin() {
        String loginUrl = "https://accounts.google.com/o/oauth2/v2/auth?" + "client_id=" + googleClientId + "&redirect_uri=" + googleRedirectUrl
                + "&response_type=code&scope=email&access_type=offline";

        return loginUrl;
    }

//    public String loadToKakaoLogin() {
//        String loginUrl = "https://kauth.kakao.com/oauth/authorize?" + "client_id=" + kakaoClientId + "&redirect_uri=" + kakaoRedirectUrl
//                + "&response_type=code";
//
//        return loginUrl;
//    }

    @Override
    public LoginResponse getGoogleAccessToken(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();

        params.put("code", accessToken);
        params.put("client_id", googleClientId);
        params.put("client_secret", googleClientSecret);
        params.put("redirect_uri", googleRedirectUrl);
        params.put("grant_type", "authorization_code");

        ResponseEntity<OAuthResponseDto> responseEntity = restTemplate.postForEntity(googleTokenUrl, params, OAuthResponseDto.class);
        Optional<GoogleUserDataDto> decodeInfo = decodeGoogleToken(responseEntity.getBody().getId_token().split("\\.")[1]);
        return loginService.loginToService(decodeInfo.get().getEmail(),LoginType.GOOGLE);
    }

    @Override
    public LoginResponse getKakaoAccessToken(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("code", accessToken);
        params.add("client_id", kakaoClientId);
        params.add("client_secret", kakaoClientSecret);
        params.add("redirect_uri", kakaoRedirectUrl);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        ResponseEntity<OAuthResponseDto> responseEntity = restTemplate.postForEntity(kakaoTokenUrl, requestEntity, OAuthResponseDto.class);
        Optional<KakaoUserDataDto> decodeInfo = decodeKakaoToken(responseEntity.getBody().getId_token().split("\\.")[1]);

        return loginService.loginToService(decodeInfo.get().getEmail(),LoginType.KAKAO);
    }

    public Optional<GoogleUserDataDto> decodeGoogleToken(String jwtToken) {
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

    public Optional<KakaoUserDataDto> decodeKakaoToken(String jwtToken) {
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
