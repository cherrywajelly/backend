package com.timeToast.timeToast.domain.toast_piece.toast_piece_image;

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

    private String imageUrl;

    @Builder
    public ToastPieceImage(final String imageUrl){
        this.imageUrl = imageUrl;
    }

    public void updateImageUrl(final String imageUrl){
        this.imageUrl = imageUrl;
    }
}
