package com.timeToast.timeToast.repository.redis;

import com.timeToast.timeToast.domain.member.member_token.MemberToken;
import com.timeToast.timeToast.repository.redis.member_token.MemberTokenRepository;
import com.timeToast.timeToast.util.TestRedisConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@SpringBootTest
public class RedisRepositoryTest extends TestRedisConfig{

    @Autowired
    private MemberTokenRepository memberTokenRepository;

    @Autowired
    private RedisRepository redisRepository;

    private String getMemberTokenKey(final long memberId) {
        return "token:"+memberId;
    }
    @Test
    public void setExpireTest(){
        MemberToken memberToken = new MemberToken(1L, "test");
        memberTokenRepository.save(memberToken);
        assertEquals(-1, redisRepository.getExpire(getMemberTokenKey(memberToken.getMemberId())));
        redisRepository.setExpire(getMemberTokenKey(memberToken.getMemberId()), Duration.ofMillis(10));
        assertNotEquals(-1, redisRepository.getExpire(getMemberTokenKey(memberToken.getMemberId())));
    }
}
