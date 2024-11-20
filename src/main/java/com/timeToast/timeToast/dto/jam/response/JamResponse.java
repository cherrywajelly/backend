package com.timeToast.timeToast.dto.jam.response;

import lombok.Builder;

@Builder
public record JamResponse(
        long jamId,

        String iconImageUrl,

        String nickname
){
    public static JamResponse from(final long jamId, final String iconImageUrl, final String nickname){
        return JamResponse.builder()
                .jamId(jamId)
                .iconImageUrl(iconImageUrl)
                .nickname(nickname)
                .build();
    }
}