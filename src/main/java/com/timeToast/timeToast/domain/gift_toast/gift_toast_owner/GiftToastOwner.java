package com.timeToast.timeToast.domain.gift_toast.gift_toast_owner;

import com.timeToast.timeToast.domain.BaseTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gift_toast_owner")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GiftToastOwner extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gift_toast_owner_id")
    private Long id;

    private Long memberId;

    private Long giftToastId;

    private Boolean isVisible;

    @Builder
    public GiftToastOwner(final long memberId, final long giftToastId, final Boolean isVisible){
        this.memberId = memberId;
        this.giftToastId = giftToastId;
        this.isVisible = isVisible;
    }

    public void updateIsVisible(final boolean isVisible){
        this.isVisible = isVisible;
    }

}