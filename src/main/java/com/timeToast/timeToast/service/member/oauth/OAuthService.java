package com.timeToast.timeToast.service.member.oauth;

import com.timeToast.timeToast.dto.member.Login;

public interface OAuthService {
    Login kakaoLoginMember(String accessToken);
    Login kakaoLoginCreator(String accessToken);
    Login kakaoLoginAdmin(String accessToken);
    Login googleLoginMember(String accessToken);
    Login googleLoginCreator(String accessToken);
    Login googleLoginAdmin(String accessToken);
}
