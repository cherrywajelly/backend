package com.timeToast.timeToast.service.redis;

import java.time.Duration;

public interface RedisService {
    void setExpireByKey(String key, Duration ttl);
    long getExpireByKey(String key);
}
