package com.timeToast.timeToast.service.oauth;

import com.timeToast.timeToast.dto.member.LoginResponse;

public interface LoginService {

    //for login test
     String loadToKakaoLogin();

    LoginResponse getAccessToken(String social, String code);

}
