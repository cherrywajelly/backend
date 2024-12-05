package com.timeToast.timeToast.dto.event_toast.response;

import lombok.Builder;

@Builder
public record EventToastDataManagerResponse(
        String eventToastIconImage,
        String eventToastName
) {
    public static EventToastDataManagerResponse from (final String eventToastIconImage, final String eventToastName) {
        return EventToastDataManagerResponse.builder()
                .eventToastIconImage(eventToastIconImage)
                .eventToastName(eventToastName)
                .build();
    }
}
