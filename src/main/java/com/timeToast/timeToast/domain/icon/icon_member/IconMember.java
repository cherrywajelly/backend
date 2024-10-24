package com.timeToast.timeToast.domain.icon.icon_member;

import com.timeToast.timeToast.domain.BaseTime;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "icon_member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IconMember extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "icon_member_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "icon_group_id", nullable = false)
    private IconGroup iconGroup;

    @Builder
    public IconMember(Member member, IconGroup iconGroup){
        this.member = member;
        this.iconGroup = iconGroup;
    }
}
