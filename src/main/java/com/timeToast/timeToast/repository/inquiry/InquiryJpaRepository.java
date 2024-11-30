package com.timeToast.timeToast.repository.inquiry;

import com.timeToast.timeToast.domain.inquiry.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryJpaRepository extends JpaRepository<Inquiry, Long> {
}
