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
    public Jam(final String title, final String contents_url, final String image_url){
        this.title = title;
        this.contentsUrl = contents_url;
        this.imageUrl = image_url;
    }
}