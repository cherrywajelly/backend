package com.timeToast.timeToast.domain.inquiry;

import com.timeToast.timeToast.domain.enums.inquiry.InquiryState;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inquiry")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_id")
    private long id;

    String title;

    String content;

    String email;

    InquiryState inquiryState;

    @Builder
    public Inquiry(final String title, final String content, final String email, final InquiryState inquiryState) {
        this.title = title;
        this.content = content;
        this.email = email;
        this.inquiryState = inquiryState;
    }

    public void updateInquiryState(InquiryState inquiryState) {
        this.inquiryState = inquiryState;
    }
}
