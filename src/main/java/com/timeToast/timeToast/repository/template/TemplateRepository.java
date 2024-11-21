package com.timeToast.timeToast.repository.template;


import com.timeToast.timeToast.domain.template.Template;

import java.util.Optional;

public interface TemplateRepository {
    Template save(final Template template);
    Optional<Template> getByEventToastId(final long eventToastId);
}
