package com.timeToast.timeToast.domain.fcm;


import com.timeToast.timeToast.domain.BaseTime;
import com.timeToast.timeToast.domain.enums.fcm.FcmConstant;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "fcm")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Fcm extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fcm_id")
    private long id;

    private long memberId;

    @Enumerated(EnumType.STRING)
    private FcmConstant fcmConstant;

    private Long senderId;

    private String toastName;

    private boolean isOpened;

    private long param;

    private String imageUrl;

    @Builder
    public Fcm(final long memberId, final FcmConstant fcmConstant, final long senderId, final String toastName, final long param, final String imageUrl) {
        this.memberId = memberId;
        this.fcmConstant = fcmConstant;
        this.senderId = senderId;
        this.toastName = toastName;
        this.param = param;
        this.imageUrl = imageUrl;
    }

    public void updateIsOpened(final boolean isOpened) { this.isOpened = isOpened; }
}