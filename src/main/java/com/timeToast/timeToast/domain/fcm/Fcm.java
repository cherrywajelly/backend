package com.timeToast.timeToast.domain.fcm;


import com.timeToast.timeToast.domain.BaseTime;
import com.timeToast.timeToast.global.constant.FcmConstant;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    private LocalDateTime time;

    private boolean isOpened;
}