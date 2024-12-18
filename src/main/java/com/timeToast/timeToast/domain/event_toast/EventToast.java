package com.timeToast.timeToast.domain.event_toast;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate openedDate;

    private boolean isOpened;

    private String description;

    @Builder
    public EventToast(final String title, final LocalDate openedDate, final long memberId, final long iconId, final String description) {
        this.title = title;
        this.openedDate = openedDate;
        this.memberId = memberId;
        this.iconId = iconId;
        this.description = description;
    }

    public void updateOpenedDateAndIsOpened(final LocalDate openedDate, final boolean isOpened) {
        this.openedDate = openedDate;
        this.isOpened = isOpened;
    }

    public void updateIsOpened(final boolean isOpened) { this.isOpened = isOpened; }

}
