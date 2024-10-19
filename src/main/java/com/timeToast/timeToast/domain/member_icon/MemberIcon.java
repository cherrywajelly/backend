package com.timeToast.timeToast.domain.member_icon;

import com.timeToast.timeToast.domain.BaseTime;
import com.timeToast.timeToast.domain.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_icon")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberIcon extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_icon_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "icon_group_id", nullable = false)
    private IconGroup iconGroup;

    @Builder
    public MemberIcon(Member member, IconGroup iconGroup){
        this.member = member;
        this.iconGroup = iconGroup;
    }
}
