package com.timeToast.timeToast.service.oAuth;

import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.member.Member;
import com.timeToast.timeToast.dto.member.LoginResponse;

public interface LoginService {

//    LoginResponse getAccessToken(LoginType social, String code);

    LoginResponse loginToService(String email, LoginType loginType);

    // 기본 아이콘 등록 로직 테스트
    void addBuiltInIconTest(Member member);
}
