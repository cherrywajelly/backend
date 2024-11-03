package com.timeToast.timeToast.domain.gift_toast.gift_toast;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timeToast.timeToast.domain.BaseTime;
import com.timeToast.timeToast.domain.enums.gift_toast.GiftToastType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "gift_toast")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GiftToast extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gift_toast_id")
    private Long id;

    private Long iconId;

    private Long groupId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate memorizedDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate openedDate;

    private Boolean isOpened;

    private String title;

    @NotNull
    @Enumerated(EnumType.STRING)
    private GiftToastType giftToastType;

    @Builder
    public GiftToast(final long iconId, final long groupId, final LocalDate memorizedDate, final LocalDate openedDate,
                     final boolean isOpened, final String title, final GiftToastType giftToastType){
        this.iconId = iconId;
        this.groupId = groupId;
        this.memorizedDate = memorizedDate;
        this.openedDate = openedDate;
        this.isOpened = isOpened;
        this.title = title;
        this.giftToastType = giftToastType;
    }

    public void updateIsOpened(final boolean isOpened){
        this.isOpened = isOpened;
    }

}
