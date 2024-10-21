package com.timeToast.timeToast.domain.icon_group;

import com.timeToast.timeToast.domain.BaseTime;
import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.domain.icon.Icon;
import com.timeToast.timeToast.domain.member.Member;
import com.timeToast.timeToast.domain.member_icon.MemberIcon;
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

    @Column(nullable = false)
    private IconType iconType;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private long price;

    private IconState iconState;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "iconGroup", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Icon> icons = new HashSet<>();

    @OneToMany(mappedBy = "iconGroup", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private final Set<MemberIcon> memberIcons = new HashSet<>();

    @Builder
    public IconGroup(final String name, final long price, final IconType iconType, Member member){
        this.name = name;
        this.price = price;
        this.iconType = iconType;
        this.member = member;
    }

    public void updateIconState(IconState iconState) { this.iconState = iconState; }
}