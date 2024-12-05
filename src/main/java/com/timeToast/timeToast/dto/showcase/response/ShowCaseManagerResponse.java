package com.timeToast.timeToast.dto.showcase.response;

import lombok.Builder;

@Builder
public record ShowCaseManagerResponse(
        String showcaseIconImage,
        String showcaseName
) {
    public static ShowCaseManagerResponse from (final String showcaseIconImage, final String showcaseName) {
        return ShowCaseManagerResponse.builder()
                .showcaseIconImage(showcaseIconImage)
                .showcaseName(showcaseName)
                .build();
    }
}
