package com.timeToast.timeToast.domain.fcm;


import com.timeToast.timeToast.domain.BaseTime;
import com.timeToast.timeToast.domain.enums.fcm.FcmConstant;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "fcm")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Fcm extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fcmId")
    private Long id;

    private Long memberId;

    @Column(nullable = false)
    private FcmConstant fcmConstant;

    private String nickname;

    private String toastName;

    private LocalDate time;

    private boolean isOpened;

    @Builder
    public Fcm(final long memberId, final FcmConstant fcmConstant, final String nickname, final String toastName, final LocalDate time) {
        this.memberId = memberId;
        this.fcmConstant = fcmConstant;
        this.nickname = nickname;
        this.toastName = toastName;
        this.time = time;
    }

    public void updateIsOpened(boolean isOpened) { this.isOpened = isOpened; }
}