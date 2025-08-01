package com.timeToast.timeToast.service.redis;

import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.member.member.response.MemberSignUpInfo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.timeToast.timeToast.global.constant.RedisKeyConstant.*;

@Service
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisServiceImpl(final RedisTemplate<String, String> redisTemplate) {
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
    public void incrSignUp(final Member member) {

        String role = member.getMemberRole().equals(MemberRole.USER) ? ROLE_USER.value() : ROLE_CREATOR.value();
        String monthKey = MONTH_SIGNUP.value() + COLON.value() + getMonth();
        String scoreKey = role + COLON.value() + LocalDate.now().getDayOfMonth();

        redisTemplate.opsForZSet().incrementScore(monthKey, scoreKey, 1);

        String totalKey = SIGN_UP.value()+COLON.value()+role;

        redisTemplate.opsForValue().increment(totalKey, 1);
    }

    private String getMonth(){
         return LocalDateTime.now().format( DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    @Override
    public MemberSignUpInfo getTotalSignUp(){

        String userTotalSignUp = redisTemplate.opsForValue().get(SIGN_UP.value()+COLON.value()+ROLE_USER.value());
        String creatorTotalSignUp = redisTemplate.opsForValue().get(SIGN_UP.value()+COLON.value()+ROLE_CREATOR.value());

        return MemberSignUpInfo.builder()
                .totalUserCount(parseLongOrDefault(userTotalSignUp))
                .totalCreatorCount(parseLongOrDefault(creatorTotalSignUp))
                .build();
    }

    private long parseLongOrDefault(String value) {
        try {
            return value != null ? Long.parseLong(value) : 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

}
