package com.timeToast.timeToast.domain.member;

import com.timeToast.timeToast.domain.BaseTime;
import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.event_toast.EventToast;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String nickname;

    private String email;

    private String memberProfileUrl;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    private boolean is_delete;

    @OneToMany(mappedBy = "event_toast", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private final Set<EventToast> eventToasts = new HashSet<>();

    @Builder
    public Member(final String nickname, final String email, final String memberProfileUrl,
                  final LoginType loginType, final MemberRole memberRole, final boolean is_delete){
        this.nickname = nickname;
        this.email = email;
        this.memberProfileUrl = memberProfileUrl;
        this.loginType = loginType;
        this.memberRole = memberRole;
        this.is_delete = is_delete;
    }

    public void updateMemberProfileUrl(final String memberProfileUrl){
        this.memberProfileUrl = memberProfileUrl;
    }

    public void updateNickname(final String nickname){
        this.nickname = nickname;
    }

    public void updateIsDelete(final boolean is_delete){
        this.is_delete = is_delete;
    }

}
