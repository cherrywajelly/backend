package com.timeToast.timeToast.domain.jam;

import com.timeToast.timeToast.domain.BaseTime;
import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.domain.member.Member;
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

    private String title;

    private String contents_url;

    private String image_url;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "event_toast_id")
    private EventToast eventToast;

    @Builder
    public Jam(final String title, final String contents_url, final String image_url){
        this.title = title;
        this.contents_url = contents_url;
        this.image_url = image_url;
    }
}
