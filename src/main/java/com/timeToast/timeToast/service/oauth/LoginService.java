package com.timeToast.timeToast.service.oauth;

import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.dto.member.LoginResponse;

public interface LoginService {

    //for login test
     String loadToKakaoLogin();

    LoginResponse getAccessToken(LoginType social, String code);

}
