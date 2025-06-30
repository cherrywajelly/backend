package com.timeToast.timeToast.domain.member.member;

import com.timeToast.timeToast.domain.BaseTime;
import com.timeToast.timeToast.domain.enums.member.Bank;
import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import jakarta.persistence.*;
import lombok.*;

import javax.annotation.Nullable;

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

    @Enumerated(EnumType.STRING)
    @Nullable
    private Bank bank;

    @Nullable
    private String accountNumber;

    //TODO 이후 확인 후 redis에 적합하면 memberToken으로 다시 이동
    @Column(length = 350)
    private String fcmToken;

    @Builder
    public Member(final Long premiumId, final String nickname, final String email, final String memberProfileUrl,
                  final LoginType loginType, final MemberRole memberRole, final Bank bank, final String accountNumber) {
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

    public void updateAccount(final Bank bank, final String accountNumber) {
        this.bank = bank;
        this.accountNumber = accountNumber;
    }

    public void updateFcmToken(final String fcmToken){ this.fcmToken = fcmToken; }
}
