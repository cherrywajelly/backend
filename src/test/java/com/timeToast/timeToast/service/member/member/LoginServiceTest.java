package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.dto.member.member.response.LoginResponse;

import static com.timeToast.timeToast.util.TestConstant.*;

public class LoginServiceTest implements LoginService {


    @Override
    public LoginResponse loginToService(String email, LoginType loginType, MemberRole memberRole) {
        return new LoginResponse(TEST_ACCESS_TOKEN.value(), TEST_REFRESH_TOKEN.value(), true);
    }


}
