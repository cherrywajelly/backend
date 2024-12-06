package com.timeToast.timeToast.dto.showcase.response;

import lombok.Builder;

@Builder
public record ShowcaseManagerResponse(
        String showcaseIconImage,
        String showcaseName
) {
    public static ShowcaseManagerResponse from (final String showcaseIconImage, final String showcaseName) {
        return ShowcaseManagerResponse.builder()
                .showcaseIconImage(showcaseIconImage)
                .showcaseName(showcaseName)
                .build();
    }
}
