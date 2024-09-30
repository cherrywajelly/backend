package com.timeToast.timeToast.domain.jwt;

import com.timeToast.timeToast.domain.BaseTime;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Table(name = "member_jwt_refresh_token")
public class MemberJwtRefreshToken extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_jwt_refresh_token_id")
    private long id;

    private long memberId;

    private String jwt_refresh_token;

    public MemberJwtRefreshToken() {

    }

    @Builder
    public MemberJwtRefreshToken(final long memberId, final String jwt_refresh_token){
        this.memberId = memberId;
        this.jwt_refresh_token = jwt_refresh_token;
    }


    public void updateRefreshToken(final String jwt_refresh_token){
        this.jwt_refresh_token = jwt_refresh_token;
    }

}
