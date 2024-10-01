package com.timeToast.timeToast.service;

public interface LoginService {

    //for login test
     String loadToKakaoLogin();

    String getAccessToken(String social, String code);

}
