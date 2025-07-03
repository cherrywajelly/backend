package com.timeToast.timeToast.service.redis;

import com.timeToast.timeToast.domain.member.member.Member;

import java.time.Duration;

public interface RedisService {
    void setExpireByKey(final String key, final Duration ttl);
    long getExpireByKey(final String key);
    Long incrMonthSignUp(final Member member);
}
