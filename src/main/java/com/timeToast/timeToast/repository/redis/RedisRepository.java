package com.timeToast.timeToast.repository.redis;

import java.time.Duration;

public interface RedisRepository {
    void setExpire(String key, Duration ttl);
    long getExpire(String key);
}
