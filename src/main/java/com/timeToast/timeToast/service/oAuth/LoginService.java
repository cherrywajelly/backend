package com.timeToast.timeToast.service.oAuth;

import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.dto.member.LoginResponse;

public interface LoginService {

//    LoginResponse getAccessToken(LoginType social, String code);

    LoginResponse loginToService(String email, LoginType loginType);
}
