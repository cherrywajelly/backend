package com.timeToast.timeToast.dto.template.response;

import com.timeToast.timeToast.dto.event_toast.response.EventToastTemplateResponse;
import lombok.Builder;

@Builder
public record TemplateResponse (
        EventToastTemplateResponse eventToastTemplateResponse,
        String IconImageUrl,
        String profileImageUrl,
        String nickname,
        String text
) {
    public static TemplateResponse of(EventToastTemplateResponse eventToastTemplateResponse, String iconImageUrl, String profileImageUrl, String nickname, String text) {
        return TemplateResponse.builder()
                .eventToastTemplateResponse(eventToastTemplateResponse)
                .IconImageUrl(iconImageUrl)
                .profileImageUrl(profileImageUrl)
                .nickname(nickname)
                .text(text)
                .build();
    }
}
