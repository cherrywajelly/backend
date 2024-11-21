package com.timeToast.timeToast.repository.template;

import com.timeToast.timeToast.domain.template.Template;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TemplateJpaRepository extends JpaRepository<Template, Long> {
    Optional<Template> findByEventToastId(final long eventToastId);
}
