package com.timeToast.timeToast.dto.member;

import lombok.Builder;

@Builder
public record Login(
        String accessToken,
        String refreshToken,
        boolean isNew
) {

    public static Login of(final String accessToken, final String refreshToken, final boolean isNew){
        return Login.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isNew(isNew)
                .build();
    }

}
