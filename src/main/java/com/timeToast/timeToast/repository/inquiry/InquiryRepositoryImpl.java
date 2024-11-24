package com.timeToast.timeToast.repository.inquiry;

import com.timeToast.timeToast.domain.inquiry.Inquiry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
