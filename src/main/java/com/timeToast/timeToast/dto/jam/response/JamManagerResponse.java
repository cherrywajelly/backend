package com.timeToast.timeToast.dto.jam.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timeToast.timeToast.domain.jam.Jam;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record JamManagerResponse (
        long jamId,
        String iconImageUrl,
        String title,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate createdAt,
        String nickname
) {
    static public JamManagerResponse from(final Jam jam, final String iconImageUrl, final String nickname) {
        return JamManagerResponse.builder()
                .jamId(jam.getId())
                .iconImageUrl(iconImageUrl)
                .title(jam.getTitle())
                .createdAt(jam.getCreatedAt().toLocalDate())
                .nickname(nickname)
                .build();
    }
}