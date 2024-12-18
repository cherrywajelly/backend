package com.timeToast.timeToast.service.inquiry;

import com.timeToast.timeToast.domain.enums.inquiry.InquiryState;
import com.timeToast.timeToast.dto.inquiry.request.InquiryRequest;
import com.timeToast.timeToast.dto.inquiry.response.InquiryDetailResponse;
import com.timeToast.timeToast.dto.inquiry.response.InquiryResponse;
import com.timeToast.timeToast.dto.inquiry.response.InquiryResponses;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_PUT;

public class InquiryServiceTest implements InquiryService {

    @Override
    public Response saveInquiry(InquiryRequest inquiryRequest, MultipartFile inquiryContents) {
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
        return new InquiryDetailResponse(1L, "title", InquiryState.RESOLVED, LocalDate.of(2024, 11, 11), "email", "content");
    }


    @Override
    public Response putInquiryState(final long inquiryId) {
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_PUT.getMessage());
    }
}

