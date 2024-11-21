package com.timeToast.timeToast.repository.template;


import com.timeToast.timeToast.domain.template.Template;

import java.util.Optional;

public interface TemplateRepository {
    Optional<Template> getByEventToastId(final long eventToastId);
}
