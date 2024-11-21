package com.timeToast.timeToast.dto.template.request;

import com.timeToast.timeToast.domain.template.Template;

public record TemplateSaveRequest (
        Long memberId,
        Long eventToastId,
        String templateText
) {
    public static Template toEntity(final Long memberId, final Long eventToastId, final String templateText) {
        return Template.builder()
                .memberId(memberId)
                .eventToastId(eventToastId)
                .templateText(templateText)
                .build();
    }
}
