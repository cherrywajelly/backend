package com.timeToast.timeToast.domain.event_toast;

import com.timeToast.timeToast.domain.BaseTime;
import com.timeToast.timeToast.domain.jam.Jam;
import com.timeToast.timeToast.domain.member.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    private String title;

    private LocalDate opened_date;

    private boolean is_opened;

    private boolean is_deleted;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "eventToast", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private final Set<Jam> jams = new HashSet<>();

    @Builder
    public EventToast(final String title, final LocalDate opened_date, Member member){
        this.title = title;
        this.opened_date = opened_date;
        this.member = member;
    }

    public void updateIsDelete(final boolean is_delete){
        this.is_deleted = !is_deleted;
    }

}
