package com.timeToast.timeToast.dto.jam.response;

import lombok.Builder;

@Builder
public record JamResponse(
        Long jamId,

        String icon_image_url,

        String nickname
){
    public static JamResponse fromEntity(final long jamId, String icon_image_url, String nickname){
        return JamResponse.builder()
                .jamId(jamId)
                .icon_image_url(icon_image_url)
                .nickname(nickname)
                .build();
    }
}