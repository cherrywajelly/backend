package com.timeToast.timeToast.global.constant;

public enum ExceptionConstant {

    //member
    MEMBER_NOT_FOUND("해당 멤버를 찾을 수 없습니다."),
    NICKNAME_CONFLICT("이미 존재하는 닉네임입니다."),
    ROLE_FORBIDDEN("역할이 검증되지 않았습니다."),
    UNAUTHORIZED_MEMBER("권한이 없는 사용자입니다,"),
    ACCESS_TOKEN_EXPIRED("사용자의 access token이 만료되었습니다."),
    REFRESH_TOKEN_EXPIRED("사용자의 refresh token이 만료되었습니다."),
    INVALID_TOKEN_FORMAT("잘못된 토큰 양식 입니다."),

    //follow
    FOLLOW_NOT_FOUND("해당 팔로우 정보를 찾을 수 없습니다."),
    FOLLOW_ALREADY_EXISTS("이미 등록된 팔로우 정보입니다."),
    INVALID_FOLLOW("자기 자신은 팔로우 할 수 없습니다."),

    //team
    TEAM_NOT_FOUND("해당 그룹 정보를 찾을 수 없습니다."),
    TEAM_MEMBER_NOT_FOUND("사용자의 그룹에서 해당 그룹 정보를 찾을 수 없습니다."),

    //event
    EVENT_TOAST_NOT_FOUND("해당 이벤트 토스트를 찾을 수 없습니다."),
    INVALID_EVENT_TOAST("잘못된 이벤트 토스트 형식입니다."),

    //jam
    JAM_NOT_FOUNT("해당 잼을 찾을 수 없습니다."),
    INVALID_NEW_JAM("이미 작성한 잼이 있습니다."),
    INVALID_JAM("잘못된 잼 형식입니다."),

    //icon
    ICON_NOT_FOUND("관련 아이콘을 찾을 수 없습니다."),
    ICON_GROUP_NOT_FOUND("해당 아이콘 그룹을 찾을 수 없습니다."),
    ICON_MEMBER_NOT_FOUND("해당 조회를 할 수 없습니다."),
    INVALID_ICON("잘목된 아이콘 형식입니다."),
    INVALID_ICON_GROUP("잘못된 아이콘 그룹 형식입니다."),
    ICONGROUP_ALREADY_EXISTS("이미 구매한 아이콘입니다."),


    //gift_toast
    INVALID_GIFT_TOAST("잘못된 선물 토스트 형식입니다."),
    GIFT_TOAST_NOT_FOUND("선물 토스트를 찾을 수 없습니다."),
    GIFT_TOAST_OWNER_NOT_FOUND("선물 토스트 소유주를 찾을 수 없습니다."),

    //toast_piece
    TOAST_PIECE_NOT_FOUND("토스트 조각을 찾을 수 없습니다."),
    TOAST_PIECE_NOT_EXISTS("토스트 조각을 존재하지 않습니다."),
    INVALID_TOAST_PIECE("자신의 토스트 조각이 아닙니다."),

    //showcase
    INVALID_SHOWCASE_COUNT("showcase는 최대 3개까지 등록이 가능합니다."),
    SHOWCASE_NOT_FOUND("showcase 토스트를 찾을 수 없습니다."),
    INVALID_SHOWCASE("자신의 showcase 토스트가 아닙니다."),

    // fcm
    INVALID_FCM_TOKEN("잘못된 토큰 등록 형식입니다."),
    FCM_NOT_FOUND("해당 알림을 찾을 수 없습니다."),
    FCM_TOKEN_ALREADY_EXIST("이미 업데이트 된 토큰입니다."),
    INVALID_FCM_CREATE_MESSAGE("잘못된 메세지 생성 형식입니다."),
    INVALID_FCM_GOOGLE_TOKEN("잘못된 fcm 구글 토큰 발급 형식입니다."),
    FCM_TOKEN_EXPIRED("fcm 토큰이 만료되었습니다. 다시 로그인 해주세요"),
    INVALID_FCM_MESSAGE("fcm 메세지가 전송되지 않았습니다."),

    //premium
    PREMIUM_NOT_FOUND("프리미엄 정보를 찾을 수 없습니다."),

    // inquiry
    INQUIRY_NOT_FOUND("문의 정보를 찾을 수 없습니다."),
    INVALID_INQUIRY("잘못된 문의 형식입니다."),

    // template
    INVALID_TEMPLATE("잘못된 공유 템플릿 텍스트 등록 형식입니다."),

    //payment
    PAYMENT_NOT_FOUND("결제 정보를 찾을 수 없습니다."),
    INVALID_PAYMENT("잘못된 결제 요청입니다."),
    ICON_PAYMENT_EXISTS("이미 결제 완료한 아이콘입니다."),
    PREMIUM_PAYMENT_EXISTS("이미 프리미엄 멤버십입니다."),

    //creator
    INVALID_CREATOR("잘못된 제작자 요청입니다."),
    ACCOUNT_ALREADY_EXIST("이미 존재하는 계좌 정보입니다."),

    //JSON
    JSON_PROCESSING_ERROR("json processing error"),

    // creator settlement
    SETTLEMENT_NOT_FOUND("관련 수익 정보를 찾을 수 없습니다"),
    INVALID_CREATOR_INFO("잘못된 아이콘 제작자 정보 접근 형식입니다."),

    INVALID_YEAR_MONTH("잘못된 날짜 조회입니다."),

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
