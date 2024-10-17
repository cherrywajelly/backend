package com.timeToast.timeToast.global.constant;

public enum ExceptionConstant {

    MEMBER_NOT_FOUND("해당 멤버를 찾을 수 없습니다."),
    ACCESS_TOKEN_EXPIRED("사용자의 access token이 만료되었습니다."),
    REFRESH_TOKEN_EXPIRED("사용자의 refresh token이 만료되었습니다."),
    INVALID_TOKEN_FORMAT("잘못된 토큰 양식 입니다."),
    FOLLOW_NOT_FOUND("해당 팔로우 정보를 찾을 수 없습니다."),
    FOLLOW_ALREADY_EXISTS("이미 등록된 팔로우 정보입니다."),
    GROUP_NOT_FOUND("해당 그룹 정보를 찾을 수 없습니다."),
    MEMBER_GROUP_NOT_FOUND("사용자의 그룹에서 해당 그룹 정보를 찾을 수 없습니다."),
    LOGIN_INTERCEPTOR_JSON_PROCESSING_ERROR("로그인한 회원의 정보를 JSON으로 파싱할 수 없습니다.");

    private final String message;

    ExceptionConstant(String message) {
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
