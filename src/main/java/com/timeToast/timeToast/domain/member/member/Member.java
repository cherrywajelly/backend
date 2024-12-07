package com.timeToast.timeToast.domain.member.member;

import com.timeToast.timeToast.domain.BaseTime;
import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private Long premiumId;

    private String nickname;

    private String email;

    private String memberProfileUrl;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    @Builder
    public Member(final Long premiumId, final String nickname, final String email, final String memberProfileUrl,
                  final LoginType loginType, final MemberRole memberRole){
        this.premiumId = premiumId;
        this.nickname = nickname;
        this.email = email;
        this.memberProfileUrl = memberProfileUrl;
        this.loginType = loginType;
        this.memberRole = memberRole;
    }

    public void updateProfileUrl(final String memberProfileUrl){
        this.memberProfileUrl = memberProfileUrl;
    }

    public void updateNickname(final String nickname){
        this.nickname = nickname;
    }

    public void updatePremiumId(final long premiumId){
        this.premiumId = premiumId;
    }

    public void updateMemberRole(final MemberRole memberRole){
        this.memberRole = memberRole;
    }

}
