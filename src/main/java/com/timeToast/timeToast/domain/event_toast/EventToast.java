package com.timeToast.timeToast.domain.event_toast;

import com.timeToast.timeToast.domain.BaseTime;
import com.timeToast.timeToast.domain.icon.Icon;
import com.timeToast.timeToast.domain.jam.Jam;
import com.timeToast.timeToast.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate openedDate;

    private boolean isOpened;

    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "eventToast", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private final Set<Jam> jams = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "icon_id")
    private Icon icon;

    @Builder
    public EventToast(final long id, final String title, final LocalDate openedDate, Member member, Icon icon){
        this.id = id;
        this.title = title;
        this.openedDate = openedDate;
        this.member = member;
        this.icon = icon;
    }

    public void updateIsDelete(final boolean isDelete){
        this.isDeleted = !isDeleted;
    }

    public void updateIsOpened(final boolean isOpened) { this.isOpened = isOpened; }

}
