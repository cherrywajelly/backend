package com.timeToast.timeToast.global.constant;

public enum RedisKeyConstant {
    MONTH_SIGNUP("month-signup"),
    SIGN_UP("signup"),
    ROLE_USER("user"),
    ROLE_CREATOR("creator"),

    DASH("-"),
    COLON(":");

    private final String value;

    RedisKeyConstant(final String value) {
        this.value = value;
    }

    public String value(){
        return value;
    }
}
