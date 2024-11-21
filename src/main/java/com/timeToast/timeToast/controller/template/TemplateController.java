package com.timeToast.timeToast.controller.template;

import com.timeToast.timeToast.dto.template.response.TemplateResponse;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.service.template.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/template")
@RestController
@RequiredArgsConstructor
public class TemplateController {
    private final TemplateService templateService;

    @GetMapping("/{eventToastId}")
    public TemplateResponse getTemplate(@PathVariable final long eventToastId) {
        return templateService.getTemplate(eventToastId);
    }
}
