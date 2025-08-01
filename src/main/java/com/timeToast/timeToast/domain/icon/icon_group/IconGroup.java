package com.timeToast.timeToast.domain.icon.icon_group;

import com.timeToast.timeToast.domain.BaseTime;
import com.timeToast.timeToast.domain.enums.icon_group.IconBuiltin;
import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    private String thumbnailImageUrl;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "icon_group_id")
    private List<Icon> icons;

    @Builder
    public IconGroup(final String name, final int price, final IconType iconType, final IconBuiltin iconBuiltin, final long memberId, final String description,
                     final IconState iconState) {
        this.name = name;
        this.price = price;
        this.iconType = iconType;
        this.iconBuiltin = iconBuiltin;
        this.memberId = memberId;
        this.description = description;
        this.iconState = iconState;
        this.icons = new ArrayList<>();
    }

    public void updateThumbnailImageUrl(final String thumbnailImageUrl) { this.thumbnailImageUrl = thumbnailImageUrl; }


    public void updateIconState(IconState iconState) { this.iconState = iconState; }

    public void addIcons(final List<Icon> icons){
        this.icons.addAll(icons);
    }
}