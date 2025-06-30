package com.timeToast.timeToast.domain.member.member_token;

import com.timeToast.timeToast.domain.BaseTime;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "token", timeToLive = 604800)
@Getter
public class MemberToken extends BaseTime {

    @Id
    private long memberId;

    private String jwt_refresh_token;



    @Builder
    public MemberToken(final long memberId, final String jwt_refresh_token){
        this.memberId = memberId;
        this.jwt_refresh_token = jwt_refresh_token;
    }


    public void updateRefreshToken(final String jwt_refresh_token){
        this.jwt_refresh_token = jwt_refresh_token;
    }


}
