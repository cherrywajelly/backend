package com.timeToast.timeToast.domain.icon.icon_group;

import com.timeToast.timeToast.domain.BaseTime;
import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.icon.icon_member.IconMember;
import com.timeToast.timeToast.domain.member.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "icon_group")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IconGroup extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "icon_group_id")
    private long id;

    private Long memberId;

    @Column(nullable = false)
    private IconType iconType;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private long price;

    private IconState iconState;

    @Builder
    public IconGroup(final String name, final long price, final IconType iconType, final long memberId){
        this.name = name;
        this.price = price;
        this.iconType = iconType;
        this.memberId = memberId;
    }

    public void updateIconState(IconState iconState) { this.iconState = iconState; }
}