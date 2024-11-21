package com.timeToast.timeToast.service.template;

import com.timeToast.timeToast.dto.template.response.TemplateResponse;

public interface TemplateService {
    TemplateResponse getTemplate(final long eventToastId);
}
