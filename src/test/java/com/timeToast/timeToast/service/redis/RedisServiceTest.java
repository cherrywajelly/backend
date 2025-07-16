package com.timeToast.timeToast.service.redis;

import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.member.member.response.MemberSignUpInfo;

import java.time.Duration;

public class RedisServiceTest implements RedisService {
    @Override
    public void setExpireByKey(String key, Duration ttl) {

    }

    @Override
    public long getExpireByKey(String key) {
        return 10;
    }

    @Override
    public void incrSignUp(Member member) {

    }

    @Override
    public MemberSignUpInfo getTotalSignUp() {
        return new MemberSignUpInfo(10, 10);
    }
}
