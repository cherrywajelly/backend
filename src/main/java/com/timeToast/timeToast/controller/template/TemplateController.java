package com.timeToast.timeToast.controller.template;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.template.request.TemplateSaveRequest;
import com.timeToast.timeToast.dto.template.response.TemplateResponse;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.service.template.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/templates")
@RestController
@RequiredArgsConstructor
public class TemplateController {
    private final TemplateService templateService;

    @PostMapping("")
    public Response saveTemplate(@Login LoginMember loginMember, @RequestBody final TemplateSaveRequest templateSaveRequest) {
        return templateService.saveTemplate(loginMember.id(), templateSaveRequest);
    }

    @GetMapping("/{eventToastId}")
    public TemplateResponse getTemplate(@PathVariable final long eventToastId) {
        return templateService.getTemplate(eventToastId);
    }
}
