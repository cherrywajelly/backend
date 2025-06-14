package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.dto.member.LoginResponse;

public interface LoginService {

    LoginResponse loginToService(final String email, final LoginType loginType, final MemberRole memberRole);
}
