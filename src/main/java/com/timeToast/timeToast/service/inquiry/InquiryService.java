package com.timeToast.timeToast.service.inquiry;

import com.timeToast.timeToast.domain.enums.inquiry.InquiryState;
import com.timeToast.timeToast.dto.inquiry.request.InquiryRequest;
import com.timeToast.timeToast.dto.inquiry.response.InquiryDetailResponse;
import com.timeToast.timeToast.dto.inquiry.response.InquiryResponses;
import com.timeToast.timeToast.global.response.Response;
import org.springframework.web.multipart.MultipartFile;

public interface InquiryService {
    Response saveInquiry(InquiryRequest inquiryRequest, MultipartFile inquiryContents);
    InquiryResponses getInquiry();
    InquiryDetailResponse getInquiryDetail(final long inquiryId);
    Response putInquiryState(final long inquiryId);
}
