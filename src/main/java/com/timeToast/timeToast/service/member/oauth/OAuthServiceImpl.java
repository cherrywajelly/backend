package com.timeToast.timeToast.service.member.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.dto.member.member.response.LoginResponse;
import com.timeToast.timeToast.dto.member.oauth.GoogleUserDataDto;
import com.timeToast.timeToast.dto.member.oauth.KakaoUserDataDto;
import com.timeToast.timeToast.dto.member.oauth.OAuthResponseDto;
import com.timeToast.timeToast.service.member.member.LoginService;
import io.jsonwebtoken.impl.Base64UrlCodec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class OAuthServiceImpl implements OAuthService {

    private final LoginService loginService;

    private final String googleTokenUrl = "https://oauth2.googleapis.com/token";

    @Value("${oauth2.client.google.client-id}")
    private String googleClientId;

    @Value("${oauth2.client.google.client-secret}")
    private String googleClientSecret;

    @Value("${oauth2.client.google.member-redirect-uri}")
    private String googleMemberRedirectUrl;

    @Value("${oauth2.client.google.creator-redirect-uri}")
    private String googleCreatorRedirectUrl;

    @Value("${oauth2.client.google.admin-redirect-uri}")
    private String googleAdminRedirectUrl;

    private final String kakaoTokenUrl = "https://kauth.kakao.com/oauth/token";

    @Value("${oauth2.client.kakao.client-id}")
    private String kakaoClientId;

    @Value("${oauth2.client.kakao.client-secret}")
    private String kakaoClientSecret;

    @Value("${oauth2.client.kakao.member-redirect-uri}")
    private String kakaoMemberRedirectUrl;

    @Value("${oauth2.client.kakao.creator-redirect-uri}")
    private String kakaoCreatorRedirectUrl;

    @Value("${oauth2.client.kakao.admin-redirect-uri}")
    private String kakaoAdminRedirectUrl;

    public OAuthServiceImpl(final LoginService loginService) {
        this.loginService = loginService;
    }



    @Transactional
    @Override
    public LoginResponse kakaoLoginMember(final String accessToken) {
        return getKakaoAccessToken(accessToken, MemberRole.USER, kakaoMemberRedirectUrl);
    }

    @Transactional
    @Override
    public LoginResponse kakaoLoginCreator(final String accessToken) {
        return getKakaoAccessToken(accessToken, MemberRole.CREATOR, kakaoCreatorRedirectUrl);
    }

    @Transactional
    @Override
    public LoginResponse kakaoLoginAdmin(final String accessToken) {
        return getKakaoAccessToken(accessToken, MemberRole.MANAGER, kakaoAdminRedirectUrl);
    }

    @Transactional
    @Override
    public LoginResponse googleLoginMember(final String accessToken) {
        return getGoogleAccessToken(accessToken, MemberRole.USER, googleMemberRedirectUrl);
    }

    @Transactional
    @Override
    public LoginResponse googleLoginCreator(final String accessToken) {
        return getGoogleAccessToken(accessToken, MemberRole.CREATOR, googleCreatorRedirectUrl);
    }

    @Transactional
    @Override
    public LoginResponse googleLoginAdmin(final String accessToken) {
        return getGoogleAccessToken(accessToken, MemberRole.MANAGER, googleAdminRedirectUrl);
    }


    private LoginResponse getKakaoAccessToken(final String accessToken, final MemberRole memberRole, final String redirectUrl) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("code", accessToken);
        params.add("client_id", kakaoClientId);
        params.add("client_secret", kakaoClientSecret);
        params.add("redirect_uri", redirectUrl);
        params.add("grant_type", "authorization_code");
        log.info("kakao redirect uri {}", redirectUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        log.info("redirect Url: {}", redirectUrl);
        ResponseEntity<OAuthResponseDto> responseEntity = restTemplate.postForEntity(kakaoTokenUrl, requestEntity, OAuthResponseDto.class);
        Optional<KakaoUserDataDto> decodeInfo = decodeKakaoToken(responseEntity.getBody().getId_token().split("\\.")[1]);

        return loginService.loginToService(decodeInfo.get().getEmail(),LoginType.KAKAO, memberRole);
    }

    private LoginResponse getGoogleAccessToken(final String accessToken, final MemberRole memberRole, final String redirectUrl) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();

        params.put("code", accessToken);
        params.put("client_id", googleClientId);
        params.put("client_secret", googleClientSecret);
        params.put("redirect_uri", redirectUrl);
        params.put("grant_type", "authorization_code");

        ResponseEntity<OAuthResponseDto> responseEntity = restTemplate.postForEntity(googleTokenUrl, params, OAuthResponseDto.class);
        Optional<GoogleUserDataDto> decodeInfo = decodeGoogleToken(responseEntity.getBody().getId_token().split("\\.")[1]);
        return loginService.loginToService(decodeInfo.get().getEmail(),LoginType.GOOGLE, memberRole);
    }

    private Optional<GoogleUserDataDto> decodeGoogleToken(String jwtToken) {
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

    private Optional<KakaoUserDataDto> decodeKakaoToken(String jwtToken) {
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
