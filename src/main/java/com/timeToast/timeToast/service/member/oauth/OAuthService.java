package com.timeToast.timeToast.service.member.oauth;

import com.timeToast.timeToast.dto.member.member.response.LoginResponse;

public interface OAuthService {
    LoginResponse kakaoLoginMember(String accessToken);
    LoginResponse kakaoLoginCreator(String accessToken);
    LoginResponse kakaoLoginAdmin(String accessToken);
    LoginResponse googleLoginMember(String accessToken);
    LoginResponse googleLoginCreator(String accessToken);
    LoginResponse googleLoginAdmin(String accessToken);
}
