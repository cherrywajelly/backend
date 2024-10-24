package com.timeToast.timeToast.domain.member;

import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.member.member.LoginMember;

import static com.timeToast.timeToast.util.TestConstant.TEST_EMAIL;

public class LoginMemberTest {

    public static LoginMember createTestLoginMember(){
        return new LoginMember(1L,TEST_EMAIL.value(), MemberRole.USER);
    }
}
