package com.timeToast.timeToast.service.inquiry;

import com.timeToast.timeToast.domain.enums.inquiry.InquiryState;
import com.timeToast.timeToast.dto.inquiry.request.InquiryRequest;
import com.timeToast.timeToast.dto.inquiry.response.InquiryDetailResponse;
import com.timeToast.timeToast.dto.inquiry.response.InquiryResponse;
import com.timeToast.timeToast.dto.inquiry.response.InquiryResponses;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;

public class InquiryServiceTest implements InquiryService {

    @Override
    public Response saveInquiry(InquiryRequest inquiryRequest) {
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_POST.getMessage());
    }


    @Override
    public InquiryResponses getInquiry() {
        List<InquiryResponse> inquiryResponses = new ArrayList<>();
        inquiryResponses.add(new InquiryResponse(1L, "title", InquiryState.RESOLVED));
        return new InquiryResponses(inquiryResponses);
    }


    @Override
    public InquiryDetailResponse getInquiryDetail(final long inquiryId) {
        return new InquiryDetailResponse("title", InquiryState.RESOLVED, LocalDate.of(2024, 11, 11), "email", "content");
    }


    @Override
    public String putInquiryState(final long inquiryId) {
        return InquiryState.RESOLVED.value();
    }
}

