package com.timeToast.timeToast.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{
    private final KakaoLoginServiceImpl kakaoLoginService;

    public String getAccessToken(String social, String code) {
        if (social.equals("kakao")){
            return kakaoLoginService.getKakaoAccessToken(code);
        }
        else {
            return "hey";
        }
    }
}
