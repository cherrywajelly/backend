package com.timeToast.timeToast.dto.jam.response;

import lombok.Builder;

@Builder
public record JamResponses(
        long jamId,

        String iconImageUrl,

        String nickname
){
    public static JamResponses fromEntity(final long jamId, String iconImageUrl, String nickname){
        return JamResponses.builder()
                .jamId(jamId)
                .iconImageUrl(iconImageUrl)
                .nickname(nickname)
                .build();
    }
}