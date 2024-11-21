package com.timeToast.timeToast.repository.template;

import com.timeToast.timeToast.domain.template.Template;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TemplateRepositoryImpl implements TemplateRepository {
    private final TemplateJpaRepository templateJpaRepository;

    public Optional<Template> getByEventToastId(final long eventToastId) {
        return templateJpaRepository.findByEventToastId(eventToastId);
    }
}
