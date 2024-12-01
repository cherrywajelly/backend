package com.timeToast.timeToast.service.inquiry;

import com.timeToast.timeToast.domain.enums.inquiry.InquiryState;
import com.timeToast.timeToast.domain.inquiry.Inquiry;
import com.timeToast.timeToast.dto.inquiry.request.InquiryRequest;
import com.timeToast.timeToast.dto.inquiry.response.InquiryDetailResponse;
import com.timeToast.timeToast.dto.inquiry.response.InquiryResponses;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.inquiry.InquiryRepository;
import com.timeToast.timeToast.service.image.FileUploadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_PUT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InquiryServiceImplTest {
    @Mock
    private InquiryRepository inquiryRepository;

    @Mock
    private FileUploadService fileUploadService;

    @InjectMocks
    private InquiryServiceImpl inquiryService;

    private Inquiry inquiry;

    @BeforeEach
    void setUp() {
        long inquiryId = 1L;

        inquiry = Inquiry.builder().inquiryState(InquiryState.UNRESOLVED).build();
    }

    @Test
    @DisplayName("문의사항 저장 성공")
    void saveInquirySuccess() throws IOException {
        InquiryRequest inquiryRequest = new InquiryRequest("title", "email");
        MockMultipartFile image = mock(MockMultipartFile.class);

        when(inquiryRepository.save(any(Inquiry.class))).thenReturn(inquiry);

        Response response = inquiryService.saveInquiry(inquiryRequest, image);

        verify(inquiryRepository, times(2)).save(any(Inquiry.class));
        assertThat(response.statusCode()).isEqualTo(StatusCode.OK.getStatusCode());
        assertThat(response.message()).isEqualTo(SUCCESS_POST.getMessage());
    }

    @Test
    @DisplayName("문의사항 목록 조회 성공")
    void getInquirySuccess() {
        when(inquiryRepository.findAll()).thenReturn(List.of(inquiry));

        InquiryResponses inquiryResponses = inquiryService.getInquiry();

        verify(inquiryRepository, times(1)).findAll();
        assertThat(inquiryResponses).isNotNull();
    }

    @Test
    @DisplayName("문의사항 상세 조회 성공")
    void getInquiryDetailSuccess() {
        long inquiryId = 1L;
        ReflectionTestUtils.setField(inquiry, "createdAt", LocalDateTime.of(2024, 1, 1, 0, 0, 0));

        when(inquiryRepository.getById(inquiryId)).thenReturn(inquiry);

        InquiryDetailResponse inquiryDetailResponse = inquiryService.getInquiryDetail(inquiryId);

        assertThat(inquiryDetailResponse).isNotNull();
    }

    @Test
    @DisplayName("문의사항 상태 수정 성공")
    void putInquiryState() {
        long inquiryId = 1L;

        when(inquiryRepository.getById(inquiryId)).thenReturn(inquiry);
        when(inquiryRepository.save(any(Inquiry.class))).thenReturn(inquiry);

        Response response = inquiryService.putInquiryState(inquiryId);

        verify(inquiryRepository, times(1)).save(any(Inquiry.class));
        assertThat(response.statusCode()).isEqualTo(StatusCode.OK.getStatusCode());
        assertThat(response.message()).isEqualTo(SUCCESS_PUT.getMessage());
    }
}
