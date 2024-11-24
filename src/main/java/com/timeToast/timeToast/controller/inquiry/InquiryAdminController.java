package com.timeToast.timeToast.controller.inquiry;

import com.timeToast.timeToast.dto.inquiry.response.InquiryResponses;
import com.timeToast.timeToast.service.inquiry.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v3/inquiries")
@RestController
@RequiredArgsConstructor
public class InquiryAdminController {
    private final InquiryService inquiryService;

    @GetMapping("")
    public InquiryResponses getAllInquiry() {
        return inquiryService.getInquiry();
    }

    //상세조회 , put
}
