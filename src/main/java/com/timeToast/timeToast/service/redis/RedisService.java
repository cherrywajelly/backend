package com.timeToast.timeToast.service.redis;

import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.member.member.response.MemberSignUpInfo;

import java.time.Duration;

public interface RedisService {
    void setExpireByKey(final String key, final Duration ttl);
    long getExpireByKey(final String key);
    void incrSignUp(final Member member);
    MemberSignUpInfo getTotalSignUp();
}
