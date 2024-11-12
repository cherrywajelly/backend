package com.timeToast.timeToast.global.constant;

public enum ExceptionConstant {

    //member
    MEMBER_NOT_EXISTS("해당 멤버를 찾을 수 없습니다."),
    NICKNAME_CONFLICT("이미 존재하는 닉네임입니다."),
    ROLE_FORBIDDEN("역할이 검증되지 않았습니다."),
    ACCESS_TOKEN_EXPIRED("사용자의 access token이 만료되었습니다."),
    REFRESH_TOKEN_EXPIRED("사용자의 refresh token이 만료되었습니다."),
    INVALID_TOKEN_FORMAT("잘못된 토큰 양식 입니다."),

    //follow
    FOLLOW_NOT_FOUND("해당 팔로우 정보를 찾을 수 없습니다."),
    FOLLOW_ALREADY_EXISTS("이미 등록된 팔로우 정보입니다."),
    ICONGROUP_ALREADY_EXISTS("이미 구매한 아이콘입니다."),

    //team
    TEAM_NOT_FOUND("해당 그룹 정보를 찾을 수 없습니다."),
    TEAM_MEMBER_NOT_FOUND("사용자의 그룹에서 해당 그룹 정보를 찾을 수 없습니다."),

    //event
    EVENT_TOAST_NOT_FOUND("해당 이벤트 토스트를 찾을 수 없습니다."),
    INVALID_EVENT_TOAST("잘못된 이벤트 토스트 형식입니다."),


    //jam
    JAM_NOT_FOUNT("해당 잼을 찾을 수 없습니다."),
    INVALID_JAM("이미 작성한 잼이 있습니다."),

    //icon
    ICON_NOT_FOUND("해당 아이콘을 찾을 수 없습니다."),
    ICON_GROUP_NOT_FOUND("해당 아이콘 그룹을 찾을 수 없습니다."),
    ICON_MEMBER_NOT_FOUND("해당 조회를 할 수 없습니다."),
    INVALID_ICON("잘목된 아이콘 형식입니다."),
    INVALID_ICON_GROUP("잘못된 아이콘 그룹 형식입니다."),


    //gift_toast
    INVALID_GIFT_TOAST("잘못된 선물 토스트 형식입니다."),
    GIFT_TOAST_NOT_FOUND("선물 토스트를 찾을 수 없습니다."),
    GIFT_TOAST_OWNER_NOT_FOUND("선물 토스트 소유주를 찾을 수 없습니다."),

    //toast_piece
    TOAST_PIECE_NOT_EXISTS("토스트 조각을 찾을 수 없습니다."),
    INVALID_TOAST_PIECE("자신의 토스트 조각이 아닙니다."),

    //showcase
    INVALID_SHOWCASE_COUNT("showcase 갯수가 3개 이상입니다."),
    SHOWCASE_NOT_FOUND("showcase 토스트를 찾을 수 없습니다."),
    INVALID_SHOWCASE("자신의 showcase 토스트가 아닙니다."),

    //login
    INVALID_USER("권한이 없는 유저입니다."),
    LOGIN_INTERCEPTOR_JSON_PROCESSING_ERROR("로그인한 회원의 정보를 JSON으로 파싱할 수 없습니다.");

    private final String message;

    ExceptionConstant(String message) {
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
