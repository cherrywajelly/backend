package com.timeToast.timeToast.service.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisServiceImpl(final RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void setExpireByKey(String key, Duration ttl) {
        redisTemplate.expire(key, ttl);
    }

    @Override
    public long getExpireByKey(String key) {
        return redisTemplate.getExpire(key);
    }

}
