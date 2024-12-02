package com.timeToast.timeToast.repository.inquiry;

import com.timeToast.timeToast.domain.inquiry.Inquiry;

import java.util.List;

public interface InquiryRepository {

    Inquiry save(Inquiry inquiry);
    List<Inquiry> findAll();
    Inquiry getById(final long id);
}
