package com.timeToast.timeToast.service.template;

import com.timeToast.timeToast.dto.template.request.TemplateSaveRequest;
import com.timeToast.timeToast.dto.template.response.TemplateResponse;
import com.timeToast.timeToast.global.response.Response;

public interface TemplateService {
    Response saveTemplate(final long memberId, final TemplateSaveRequest templateSaveRequest);
    TemplateResponse getTemplate(final long eventToastId);
}
