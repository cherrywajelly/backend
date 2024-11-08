package com.timeToast.timeToast.dto.jam.response;

import lombok.Builder;

@Builder
public record JamResponse(
        Long jamId,

        String iconImageUrl,

        String nickname
){
    public static JamResponse fromEntity(final long jamId, String iconImageUrl, String nickname){
        return JamResponse.builder()
                .jamId(jamId)
                .iconImageUrl(iconImageUrl)
                .nickname(nickname)
                .build();
    }
}