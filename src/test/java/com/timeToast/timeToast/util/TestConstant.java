package com.timeToast.timeToast.util;

public enum TestConstant {

    TEST_ACCESS_TOKEN("access token"),
    TEST_REFRESH_TOKEN("refresh token"),
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
