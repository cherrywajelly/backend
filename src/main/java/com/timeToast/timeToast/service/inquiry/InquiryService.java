package com.timeToast.timeToast.service.inquiry;

import com.timeToast.timeToast.dto.inquiry.request.InquiryRequest;
import com.timeToast.timeToast.dto.inquiry.response.InquiryResponses;
import com.timeToast.timeToast.global.response.Response;

public interface InquiryService {
    Response saveInquiry(InquiryRequest inquiryRequest);
    InquiryResponses getInquiry();
}
