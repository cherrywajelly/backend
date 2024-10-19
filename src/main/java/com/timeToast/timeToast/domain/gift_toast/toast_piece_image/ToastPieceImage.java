package com.timeToast.timeToast.domain.gift_toast.toast_piece_image;

import com.timeToast.timeToast.domain.BaseTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "toast_piece_image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ToastPieceImage extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "toast_piece_image_id")
    private Long id;

    private Long toastPieceId;

    private String imageUrl;

    @Builder
    public ToastPieceImage(final long toastPieceId, final String imageUrl){
        this.toastPieceId = toastPieceId;
        this.imageUrl = imageUrl;
    }

    public void updateImageUrl(final String imageUrl){
        this.imageUrl = imageUrl;
    }
}
