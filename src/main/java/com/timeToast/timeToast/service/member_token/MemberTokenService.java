package com.timeToast.timeToast.service.member_token;

import com.timeToast.timeToast.domain.member.member_token.MemberToken;

public interface MemberTokenService {

    MemberToken save(final long memberId, final String refreshToken);
}
