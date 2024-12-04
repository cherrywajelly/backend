package com.timeToast.timeToast.dto.event_toast.response;

import lombok.Builder;

@Builder
public record EventToastManagerResponse (
        long eventToastId,
        String iconImageUrl,
        String title,
        String nickname
) {
    public static EventToastManagerResponse from(final long eventToastId, final String title, final String iconImageUrl, final String nickname) {
        return EventToastManagerResponse.builder()
                .eventToastId(eventToastId)
                .title(title)
                .iconImageUrl(iconImageUrl)
                .nickname(nickname)
                .build();
    }
}
