package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.member.member.response.LoginResponse;

public interface LoginService {

    LoginResponse loginToService(final String email, final LoginType loginType, final MemberRole memberRole);
    void addBuiltInIconTest(Member member);
}
