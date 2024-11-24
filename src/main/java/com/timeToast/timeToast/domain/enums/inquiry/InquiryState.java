package com.timeToast.timeToast.domain.enums.inquiry;

public enum InquiryState {
    RESOLVED("해결 완료"),
    UNRESOLVED("미해결");

    private final String value;

    InquiryState(final String value) {
        this.value = value;
    }

    public String value(){
        return value;
    }
}

