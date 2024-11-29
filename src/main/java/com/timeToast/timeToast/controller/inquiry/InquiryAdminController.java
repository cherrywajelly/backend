package com.timeToast.timeToast.controller.inquiry;

import com.timeToast.timeToast.dto.inquiry.response.InquiryDetailResponse;
import com.timeToast.timeToast.dto.inquiry.response.InquiryResponses;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.service.inquiry.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v3/inquiries")
@RestController
@RequiredArgsConstructor
public class InquiryAdminController {

    private final InquiryService inquiryService;

    @GetMapping("")
    public InquiryResponses getAllInquiry() {
        return inquiryService.getInquiry();
    }

    @GetMapping("/{inquiryId}")
    public InquiryDetailResponse getInquiryDetail(@PathVariable long inquiryId) {
        return inquiryService.getInquiryDetail(inquiryId);
    }

    @PutMapping("/{inquiryId}")
    public Response putInquiryState(@PathVariable long inquiryId) {
        return inquiryService.putInquiryState(inquiryId);
    }
}
