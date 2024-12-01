package com.timeToast.timeToast.domain.enums.creator_account;

public enum Bank {
    IBK("IBKOKRSE"),
    NH("NACFKRSE"),
    Sh("NFFCKRSE"),
    KB("CZNBKRSE"),
    WOORI("HVBKKRSE"),
    SC("SCBLKRSE"),
    HANA("KOEXKRSE"),
    SHINHAN("SHBKKRSE"),
    KAKAO("KAKOKR22"),
    TOSS("TOBNKRSE");

    private final String value;

    Bank(final String value) {
        this.value = value;
    }

    public String value(){
        return value;
    }
}
