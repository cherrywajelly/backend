package com.timeToast.timeToast.domain.icon.icon;

import com.timeToast.timeToast.domain.BaseTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private String iconImageUrl;

    @Builder
    public Icon(final String iconImageUrl, final Long iconGroupId){
        this.iconImageUrl = iconImageUrl;
        this.iconGroupId = iconGroupId;
    }

    public void updateUrl(final String iconImageUrl) {
        this.iconImageUrl = iconImageUrl;
    }
}
