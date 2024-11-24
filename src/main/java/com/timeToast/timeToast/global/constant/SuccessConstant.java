package com.timeToast.timeToast.global.constant;

public enum SuccessConstant {

    SUCCESS_DELETE("삭제에 성공하였습니다."),
    SUCCESS_POST("저장을 성공하였습니다."),
    SUCCESS_PUT("수정을 성공하였습니다."),

    VALID_NICKNAME("사용가능한 닉네임입니다");


    private final String message;

    SuccessConstant(String value) {
        this.message = value;
    }

    public String getMessage(){
        return message;
    }
}
