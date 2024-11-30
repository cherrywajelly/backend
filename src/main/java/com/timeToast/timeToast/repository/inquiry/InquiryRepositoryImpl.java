package com.timeToast.timeToast.repository.inquiry;

import com.timeToast.timeToast.domain.inquiry.Inquiry;
import com.timeToast.timeToast.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.INQUIRY_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class InquiryRepositoryImpl implements InquiryRepository {
    private final InquiryJpaRepository inquiryJpaRepository;

    @Override
    public Inquiry save(Inquiry inquiry) {
        return inquiryJpaRepository.save(inquiry);
    }

    @Override
    public List<Inquiry> findAll() {
        return inquiryJpaRepository.findAll();
    }

    @Override
    public Inquiry getById(final long id) {
        return inquiryJpaRepository.findById(id).orElseThrow(() -> new NotFoundException(INQUIRY_NOT_FOUND.getMessage()));
    }
}
