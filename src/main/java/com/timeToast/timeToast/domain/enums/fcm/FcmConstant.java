package com.timeToast.timeToast.domain.enums.fcm;

public enum FcmConstant {
    EVENTTOASTSPREAD(" 토스트에 잼을 발랐습니다"),
    EVENTTOASTOPENED("이벤트 토스트가 열렸습니다. 확인해 볼까요?"),
    GIFTTOASTCREATED("새로운 선물 토스트가 구워졌습니다"),
    GIFTTOASTOPENED("선물 토스트가 열렸습니다. 확인해 볼까요?"),
    GIFTTOASTBAKED(" 토스트 조각을 구웠습니다"),
    FOLLOW(" 나를 팔로우합니다");

    private final String value;

    FcmConstant(final String value) {
        this.value = value;
    }

    public String value(){
        return value;
    }
}