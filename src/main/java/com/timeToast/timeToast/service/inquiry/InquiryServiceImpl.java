package com.timeToast.timeToast.service.inquiry;

import com.timeToast.timeToast.domain.enums.inquiry.InquiryState;
import com.timeToast.timeToast.domain.inquiry.Inquiry;
import com.timeToast.timeToast.dto.inquiry.request.InquiryRequest;
import com.timeToast.timeToast.dto.inquiry.response.InquiryDetailResponse;
import com.timeToast.timeToast.dto.inquiry.response.InquiryResponse;
import com.timeToast.timeToast.dto.inquiry.response.InquiryResponses;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.inquiry.InquiryRepository;
import com.timeToast.timeToast.service.image.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.INVALID_INQUIRY;
import static com.timeToast.timeToast.global.constant.FileConstant.*;
import static com.timeToast.timeToast.global.constant.FileConstant.SLASH;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_PUT;

@Service
@Slf4j
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService {

    @Value("${spring.cloud.oci.base-url}")
    private String baseUrl;

    private final InquiryRepository inquiryRepository;
    private final FileUploadService fileUploadService;

    @Transactional
    @Override
    public Response saveInquiry(InquiryRequest inquiryRequest, MultipartFile inquiryContents) {

        Inquiry inquiry = inquiryRequest.to(inquiryRequest, InquiryState.UNRESOLVED);

        if(inquiry != null) {
            inquiryRepository.save(inquiry);
            String saveUrl = baseUrl + INQUIRY.value() + SLASH.value() + CONTENTS.value() + SLASH.value() +  inquiry.getId();
            inquiry.updateInquiryContentsUrl(fileUploadService.uploadfile(inquiryContents, saveUrl));
            inquiryRepository.save(inquiry);
            log.info("Save inquiry {}", inquiry.getId());
        } else {
            throw new BadRequestException(INVALID_INQUIRY.getMessage());
        }


        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_POST.getMessage());
    }

    @Transactional(readOnly = true)
    @Override
    public InquiryResponses getInquiry() {
        List<InquiryResponse> inquiryResponses = new ArrayList<>();

        List<Inquiry> inquiries = inquiryRepository.findAll();
        inquiries.forEach(inquiry -> inquiryResponses.add(InquiryResponse.from(inquiry)));
        return new InquiryResponses(inquiryResponses);
    }

    @Transactional(readOnly = true)
    @Override
    public InquiryDetailResponse getInquiryDetail(final long inquiryId) {
        Inquiry inquiry = inquiryRepository.getById(inquiryId);
        return InquiryDetailResponse.from(inquiry);
    }

    @Transactional
    @Override
    public Response putInquiryState(final long inquiryId) {
        Inquiry inquiry = inquiryRepository.getById(inquiryId);
        inquiry.updateInquiryState(InquiryState.RESOLVED);
        inquiryRepository.save(inquiry);
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_PUT.getMessage());
    }

}
