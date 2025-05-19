package com.timeToast.timeToast.service.template;

import com.timeToast.timeToast.dto.event_toast.response.EventToastTemplateResponse;
import com.timeToast.timeToast.dto.template.request.TemplateSaveRequest;
import com.timeToast.timeToast.dto.template.response.TemplateResponse;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.constant.SuccessConstant;
import com.timeToast.timeToast.global.response.Response;

import java.time.LocalDate;

public class TemplateServiceTest implements TemplateService{
    @Override
    public Response saveTemplate(final long memberId, final TemplateSaveRequest templateSaveRequest) {
        return new Response(StatusCode.OK.getStatusCode(), SuccessConstant.SUCCESS_POST.getMessage());
    }

    @Override
    public TemplateResponse getTemplate(final long eventToastId) {
        TemplateResponse templateResponse = new TemplateResponse(new EventToastTemplateResponse("제목", LocalDate.of(2024, 11, 11)), "아이콘 이미지", "프로필 이미지", "닉네임", "텍스트");
        return templateResponse;
    }
}
