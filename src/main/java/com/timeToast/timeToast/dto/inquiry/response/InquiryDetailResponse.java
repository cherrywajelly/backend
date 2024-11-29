package com.timeToast.timeToast.dto.inquiry.response;

import com.timeToast.timeToast.domain.enums.inquiry.InquiryState;
import com.timeToast.timeToast.domain.inquiry.Inquiry;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record InquiryDetailResponse (
        String title,
        InquiryState inquiryState,
        LocalDate createdAt,
        String email,
        String content
) {
    public static InquiryDetailResponse from(Inquiry inquiry) {
        return InquiryDetailResponse.builder()
                .title(inquiry.getTitle())
                .inquiryState(inquiry.getInquiryState())
                .createdAt(inquiry.getCreatedAt().toLocalDate())
                .email(inquiry.getEmail())
                .content(inquiry.getContent())
                .build();
    }
}
