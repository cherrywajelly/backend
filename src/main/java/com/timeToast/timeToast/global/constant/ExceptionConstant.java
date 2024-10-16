package com.timeToast.timeToast.global.constant;

public enum ExceptionConstant {

    MEMBER_NOT_FOUND("해당 멤버를 찾을 수 없습니다."),
    EVENTTOAST_NOT_FOUND("해당 이벤트 토스트를 찾을 수 없습니다."),
    ICON_NOT_FOUND("해당 아이콘을 찾을 수 없습니다."),
    ICONGROUP_NOT_FOUND("해당 아이콘 그룹을 찾을 수 없습니다."),
    JAM_NOT_FOUNT("해당 잼을 찾을 수 없습니다."),
    MEMBERICON_NOT_FOUND("해당 조회를 할 수 없습니다."),
    LOGIN_INTERCEPTOR_JSON_PROCESSING_ERROR("로그인한 회원의 정보를 JSON으로 파싱할 수 없습니다.");

    private final String message;

    ExceptionConstant(String message) {
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
