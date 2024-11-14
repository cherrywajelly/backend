package com.timeToast.timeToast.dto.jam.response;

import com.timeToast.timeToast.domain.jam.Jam;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record JamDataResponse (
        String jamIconImageUrl,
        String jamTitle,
        String jamMemberProfileUrl,
        String jamNickname,
        String jamContentsUrl,
        String jamImageUrl,
        LocalDate jamCreatedDate
) {
    public static JamDataResponse fromEntity(Jam jam, String iconImageUrl, String memberProfileUrl, String nickname) {
        return JamDataResponse.builder()
                .jamIconImageUrl(iconImageUrl)
                .jamTitle(jam.getTitle())
                .jamMemberProfileUrl(memberProfileUrl)
                .jamNickname(nickname)
                .jamContentsUrl(jam.getContentsUrl())
                .jamImageUrl(jam.getImageUrl())
                .jamCreatedDate(jam.getCreatedAt().toLocalDate())
                .build();
    }
}