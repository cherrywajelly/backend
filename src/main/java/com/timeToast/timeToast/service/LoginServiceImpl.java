package com.timeToast.timeToast.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{
    private final KakaoLoginServiceImpl kakaoLoginServiceImpl;

    // for login test
    public String loadToKakaoLogin() {
        return kakaoLoginServiceImpl.loadToLogin();
    }

    public String getAccessToken(String social, String code) {
        if (social.equals("kakao")){
            return loginToService(kakaoLoginServiceImpl.getKakaoAccessToken(code));
        }
        else {
            return "hey";
        }
    }

    public String loginToService(String email) {
        return "곧 개발 예정 ,,";
    }
}
