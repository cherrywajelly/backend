package com.timeToast.timeToast.domain.event_toast;

import com.timeToast.timeToast.domain.BaseTime;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.jam.Jam;
import com.timeToast.timeToast.domain.member.member.Member;
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
    private Long id;

    private Long memberId;

    private Long iconId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate openedDate;

    private boolean isOpened;

    private boolean isDeleted;

    @Builder
    public EventToast(final long id, final String title, final LocalDate openedDate, final long memberId, final long iconId){
        this.id = id;
        this.title = title;
        this.openedDate = openedDate;
        this.memberId = memberId;
        this.iconId = iconId;
    }

    public void updateIsDelete(final boolean isDelete){
        this.isDeleted = !isDeleted;
    }

    public void updateIsOpened(final boolean isOpened) { this.isOpened = isOpened; }

}
