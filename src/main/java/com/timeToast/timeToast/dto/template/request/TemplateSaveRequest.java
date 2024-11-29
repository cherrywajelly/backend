package com.timeToast.timeToast.dto.template.request;

import com.timeToast.timeToast.domain.template.Template;

public record TemplateSaveRequest (
        Long eventToastId,
        String text
) {
    public static Template toEntity(final Long memberId, final TemplateSaveRequest templateSaveRequest) {
        return Template.builder()
                .memberId(memberId)
                .eventToastId(templateSaveRequest.eventToastId)
                .templateText(templateSaveRequest.text)
                .build();
    }
}
