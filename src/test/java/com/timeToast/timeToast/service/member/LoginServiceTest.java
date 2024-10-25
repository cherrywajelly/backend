package com.timeToast.timeToast.service.member;

import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.member.member.LoginResponse;
import com.timeToast.timeToast.service.member.member.LoginService;

import static com.timeToast.timeToast.util.TestConstant.*;

public class LoginServiceTest implements LoginService {

    @Override
    public LoginResponse loginToService(String email, LoginType loginType) {
        return new LoginResponse(TEST_ACCESS_TOKEN.value(), TEST_REFRESH_TOKEN.value());
    }

    //TODO DB 연결 시 삭제
    @Override
    public void addBuiltInIconTest(Member member) {

    }


}
