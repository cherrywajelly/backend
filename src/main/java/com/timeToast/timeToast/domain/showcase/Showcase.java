package com.timeToast.timeToast.domain.showcase;

import com.timeToast.timeToast.domain.BaseTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "showcase")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Showcase extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "showcase_id")
    private Long id;

    private Long memberId;

    private Long eventToastId;

    @Builder
    public Showcase(final long memberId, final long eventToastId){
        this.memberId = memberId;
        this.eventToastId = eventToastId;
    }


}
