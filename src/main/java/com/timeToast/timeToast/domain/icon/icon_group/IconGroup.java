package com.timeToast.timeToast.domain.icon.icon_group;

import com.timeToast.timeToast.domain.BaseTime;
import com.timeToast.timeToast.domain.enums.icon_group.IconBuiltin;
import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @Enumerated(EnumType.STRING)
    private IconType iconType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private IconBuiltin iconBuiltin;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    private IconState iconState;

    private String description;

    @Builder
    public IconGroup(final String name, final int price, final IconType iconType, final IconBuiltin iconBuiltin, final long memberId, final String description) {
        this.name = name;
        this.price = price;
        this.iconType = iconType;
        this.iconBuiltin = iconBuiltin;
        this.memberId = memberId;
        this.description = description;
    }

    public void updateIconState(IconState iconState) { this.iconState = iconState; }
}