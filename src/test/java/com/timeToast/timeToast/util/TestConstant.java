package com.timeToast.timeToast.util;

public enum TestConstant {

    TEST_ACCESS_TOKEN("test accessToken"),
    TEST_REFRESH_TOKEN("test refreshToken"),
    TEST_AUTH_CODE("test authCode"),

    TEST_EMAIL("test email");


    private final String value;

    TestConstant(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
