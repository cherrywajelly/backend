package com.timeToast.timeToast.service.redis;

import com.timeToast.timeToast.domain.member.member.Member;

public interface RedisStreamService {
    void memberJoinedPublish(Member member);
}
