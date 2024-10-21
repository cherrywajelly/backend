package com.timeToast.timeToast.domain.gift_toast.toast_piece;

import com.timeToast.timeToast.domain.BaseTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "toast_piece")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ToastPiece extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "toast_piece_id")
    private Long id;

    private Long memberId;

    private Long giftToastId;

    private String title;

    private String contentsUrl;

    @Builder
    public ToastPiece(final long memberId, final long giftToastId, final String title, final String contentsUrl){
        this.memberId = memberId;
        this.giftToastId = giftToastId;
        this.title = title;
        this.contentsUrl = contentsUrl;
    }

    public void updateContentsUrl(final String contentsUrl){
        this.contentsUrl = contentsUrl;
    }

}
