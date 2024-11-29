package com.timeToast.timeToast.controller.inquiry;


import com.timeToast.timeToast.dto.inquiry.request.InquiryRequest;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.service.inquiry.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/inquiries")
@RestController
@RequiredArgsConstructor
public class InquiryController {
    private final InquiryService inquiryService;

    @PostMapping("")
    public Response saveInquiry(@RequestBody InquiryRequest inquiryRequest) {
        return inquiryService.saveInquiry(inquiryRequest);
    }
}
