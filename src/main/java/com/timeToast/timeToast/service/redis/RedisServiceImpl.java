package com.timeToast.timeToast.service.redis;

import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.member.member.Member;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.timeToast.timeToast.global.constant.RedisKeyConstant.*;

@Service
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisServiceImpl(final RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void setExpireByKey(final String key, final Duration ttl) {
        redisTemplate.expire(key, ttl);
    }

    @Override
    public long getExpireByKey(final String key) {
        return redisTemplate.getExpire(key);
    }

    @Override
    public Long incrMonthSignUp(final Member member) {
        String role = member.getMemberRole().equals(MemberRole.USER) ? ROLE_USER.value() : ROLE_CREATOR.value();
        String key = MONTH_SIGNUP.value() + DASH.value() + role + COLON.value() + getMonth();
        return redisTemplate.opsForValue().increment(key, 1);
    }

    private String getMonth(){
         return LocalDateTime.now().format( DateTimeFormatter.ofPattern("yyyy-MM"));
    }

}
