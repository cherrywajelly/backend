package com.timeToast.timeToast.domain.jam;

import com.timeToast.timeToast.domain.BaseTime;
import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.member.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "jam")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Jam extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jam_id")
    private long id;

    private Long memberId;

    private Long eventToastId;

    private Long iconId;

    private String title;

    private String contentsUrl;

    private String imageUrl;

    @Builder
    public Jam(final String title, final long iconId, final long memberId, final long eventToastId) {
        this.title = title;
        this.iconId = iconId;
        this.memberId = memberId;
        this.eventToastId = eventToastId;
    }

    public void updateContentsUrl(String contentsUrl) { this.contentsUrl = contentsUrl; }

    public void updateImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
