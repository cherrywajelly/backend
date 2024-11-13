package com.timeToast.timeToast.domain.fcm;


import com.timeToast.timeToast.domain.BaseTime;
import com.timeToast.timeToast.domain.enums.fcm.FcmConstant;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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
    @Column(name = "fcm_id")
    private long id;

    private long memberId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FcmConstant fcmConstant;

    private String nickname;

    private String toastName;

    private boolean isOpened;

    // pathVariable id
    private long param;

    private String imageUrl;

    @Builder
    public Fcm(final long memberId, final FcmConstant fcmConstant, final String nickname, final String toastName, final long param, final String imageUrl) {
        this.memberId = memberId;
        this.fcmConstant = fcmConstant;
        this.nickname = nickname;
        this.toastName = toastName;
        this.param = param;
        this.imageUrl = imageUrl;
    }

    public void updateIsOpened(final boolean isOpened) { this.isOpened = isOpened; }
}