package com.timeToast.timeToast.dto.inquiry.request;

import com.timeToast.timeToast.domain.enums.inquiry.InquiryState;
import com.timeToast.timeToast.domain.inquiry.Inquiry;

public record InquiryRequest (
        String title,
        String content,
        String email
) {
    public Inquiry to(InquiryRequest inquiryRequest, InquiryState inquiryState) {
        return Inquiry.builder()
                .title(inquiryRequest.title)
                .content(inquiryRequest.content)
                .email(inquiryRequest.email)
                .inquiryState(inquiryState)
                .build();
    }
}
