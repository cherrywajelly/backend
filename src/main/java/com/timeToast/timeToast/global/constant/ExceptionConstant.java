package com.timeToast.timeToast.global.constant;

public enum ExceptionConstant {

    MEMBER_NOT_FOUND("해당 멤버를 찾을 수 없습니다.");

    private final String message;

    ExceptionConstant(String message) {
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
