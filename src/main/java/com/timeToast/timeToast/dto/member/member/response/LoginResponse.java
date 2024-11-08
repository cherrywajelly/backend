package com.timeToast.timeToast.dto.member.member.response;

import lombok.Builder;

@Builder
public record LoginResponse(
        String accessToken,
        String refreshToken,
        boolean isNew
) {

    public static LoginResponse of(final String accessToken, final String refreshToken, final boolean isNew){
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isNew(isNew)
                .build();
    }

}
