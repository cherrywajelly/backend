package com.timeToast.timeToast.service.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.redis.MemberJoinDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.connection.stream.StringRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.MEMBER_JOIN_STREAM_PUBLISH_FAIL;

@Slf4j
@Service
public class RedisStreamServiceImpl implements RedisStreamService{

    private final RedisTemplate redisTemplate;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${stream.key.member-joined}")
    private String memberJoinedKey;

    public RedisStreamServiceImpl(final RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void memberJoinedPublish(Member member) {
        try {
            objectMapper.registerModule(new JavaTimeModule());
            String json = objectMapper.writeValueAsString(MemberJoinDto.from(member));

            StringRecord record = StreamRecords.string(Map.of("payload", json)).withStreamKey(memberJoinedKey);
            redisTemplate.opsForStream().add(record);
        } catch (JsonProcessingException e) {
            log.warn(MEMBER_JOIN_STREAM_PUBLISH_FAIL.getMessage());
        }


    }
}
