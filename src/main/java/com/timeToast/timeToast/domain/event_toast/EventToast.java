package com.timeToast.timeToast.domain.event_toast;

import com.timeToast.timeToast.domain.BaseTime;
import com.timeToast.timeToast.domain.jam.Jam;
import com.timeToast.timeToast.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "event_toast")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventToast extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_toast_id")
    private long id;

    private String title;

    private LocalDateTime opened_date;

    private boolean is_opened;

    private boolean is_deleted;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "event_toast", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private final Set<Jam> jams = new HashSet<>();

    @Builder
    public EventToast(final String title, final LocalDateTime opened_date, final boolean is_opened, final boolean is_deleted){
        this.title = title;
        this.opened_date = opened_date;
        this.is_opened = is_opened;
        this.is_deleted = is_deleted;
    }

    public void updateIsDelete(final boolean is_delete){
        this.is_deleted = !is_deleted;
    }

}
