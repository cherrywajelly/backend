package com.timeToast.timeToast.domain.member.member_token;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("token")
@Getter
public class MemberToken implements Serializable {

    @Id
    private long memberId;
    private String jwt_refresh_token;

    @Builder
    public MemberToken(final long memberId, final String jwt_refresh_token){
        this.memberId = memberId;
        this.jwt_refresh_token = jwt_refresh_token;
    }

}
