package com.timeToast.timeToast.domain.member.member_token;

import com.timeToast.timeToast.domain.BaseTime;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;


@Entity
@Getter
@Table(name = "member_token")
public class MemberToken extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_token_id")
    private long id;

    private long memberId;

    @Column(length = 350)
    private String jwt_refresh_token;

    @Column(length = 350)
    private String fcm_token;

    public MemberToken() {

    }

    @Builder
    public MemberToken(final long memberId, final String jwt_refresh_token){
        this.memberId = memberId;
        this.jwt_refresh_token = jwt_refresh_token;
    }


    public void updateRefreshToken(final String jwt_refresh_token){
        this.jwt_refresh_token = jwt_refresh_token;
    }

    public void updateFcmToken(final String fcm_token){ this.fcm_token = fcm_token; }
}
