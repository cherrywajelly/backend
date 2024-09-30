package com.timeToast.timeToast.service;


import com.timeToast.timeToast.dto.OAuthResponseDto;

public interface LoginService {
    String getAccessToken(String social, String code);

}
