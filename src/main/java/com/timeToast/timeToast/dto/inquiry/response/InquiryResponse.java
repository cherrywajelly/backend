package com.timeToast.timeToast.dto.inquiry.response;

import com.timeToast.timeToast.domain.enums.inquiry.InquiryState;
import com.timeToast.timeToast.domain.inquiry.Inquiry;
import lombok.Builder;

@Builder
public record InquiryResponse(
        long inquiryId,
        String title,
        InquiryState inquiryState
) {
    public static InquiryResponse from(Inquiry inquiry) {
        return InquiryResponse.builder()
                .inquiryId(inquiry.getId())
                .title(inquiry.getTitle())
                .inquiryState(inquiry.getInquiryState())
                .build();
    }
}
