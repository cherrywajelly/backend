package com.timeToast.timeToast.controller.inquiry;

import com.timeToast.timeToast.dto.inquiry.request.InquiryRequest;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.service.inquiry.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/v1/inquiries")
@RestController
@RequiredArgsConstructor
public class InquiryController {
    private final InquiryService inquiryService;

    @PostMapping("")
    public Response saveInquiry(@RequestPart InquiryRequest inquiryRequest, @RequestPart("inquiryContents") final MultipartFile inquiryContents) {
        return inquiryService.saveInquiry(inquiryRequest, inquiryContents);
    }
}
