package com.timeToast.timeToast.service.redis;

import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.member.member_token.MemberToken;
import com.timeToast.timeToast.repository.redis.member_token.MemberTokenRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Duration;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class RedisServiceImplTest{

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ZSetOperations<String, Object> zSetOperations;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @Mock
    private MemberTokenRepository memberTokenRepository;

    @InjectMocks
    private RedisServiceImpl redisService;


    private String getMemberTokenKey(final long memberId) {
        return "token:"+memberId;
    }

    @Test
    @DisplayName("memberToken ttl 설정")
    public void setExpireTest(){
        MemberToken memberToken = new MemberToken(1L, "test");
        memberTokenRepository.save(memberToken);

        redisService.setExpireByKey(getMemberTokenKey(memberToken.getMemberId()), Duration.ofMillis(10));
        Assertions.assertNotEquals(-1, redisService.getExpireByKey(getMemberTokenKey(memberToken.getMemberId())));
    }

    @Test
    @DisplayName("incr 설정")
    public void incrSignUp(){
        when(redisTemplate.opsForZSet()).thenReturn(zSetOperations);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        Member member = new Member(1L, "nickname", "email", "profileUrl",
                LoginType.GOOGLE, MemberRole.USER);
        ReflectionTestUtils.setField(member, "id", 1L);

        redisService.incrSignUp(member);

        verify(valueOperations, times(1)).increment(anyString(), anyLong());
    }
}
