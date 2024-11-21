package com.timeToast.timeToast.controller.templete;

import com.timeToast.timeToast.service.templete.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/template")
@RestController
@RequiredArgsConstructor
public class TemplateController {
    private final TemplateService templateService;


}
