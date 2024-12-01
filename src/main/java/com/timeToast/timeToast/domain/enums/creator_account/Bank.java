package com.timeToast.timeToast.domain.enums.creator_account;

public enum Bank {
    IBKOKRSE, NACFKRSE, NFFCKRSE, CZNBKRSE, HVBKKRSE, SCBLKRSE, KOEXKRSE, SHBKKRSE, KAKOKR22, TOBNKRSE

    private final String value;

    Bank(final String value) {
        this.value = value;
    }

    public String value(){
        return value;
    }
}

