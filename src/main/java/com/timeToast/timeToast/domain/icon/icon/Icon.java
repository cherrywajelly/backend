package com.timeToast.timeToast.domain.icon.icon;

import com.timeToast.timeToast.domain.BaseTime;
import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.jam.Jam;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "icon")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Icon extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "icon_id")
    private long id;

    private Long iconGroupId;

    private String icon_image_url;

    @Builder
    public Icon(final String icon_image_url, final Long iconGroupId){
        this.icon_image_url = icon_image_url;
        this.iconGroupId = iconGroupId;
    }
}
