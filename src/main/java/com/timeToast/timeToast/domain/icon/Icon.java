package com.timeToast.timeToast.domain.icon;

import com.timeToast.timeToast.domain.BaseTime;
import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.domain.icon_group.IconGroup;
import com.timeToast.timeToast.domain.jam.Jam;
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

    private String icon_image_url;

    @ManyToOne
    @JoinColumn(name = "icon_group_id", nullable = false)
    private IconGroup iconGroup;

    @OneToOne(mappedBy = "icon", fetch = FetchType.LAZY)
    private EventToast eventToast;

    @OneToOne(mappedBy = "icon", fetch = FetchType.LAZY)
    private Jam jam;

    @Builder
    public Icon(final long id, final String icon_image_url){
        this.id = id;
        this.icon_image_url = icon_image_url;
    }
}
