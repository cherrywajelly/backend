package com.timeToast.timeToast.global.constant;

public enum FileConstant {
    IMAGE("image"),
    CONTENTS("contents"),
    MEMBER("member"),
    TEAM("team"),
    TOAST_PIECE("toastPiece"),
    ICON("icon"),
    JAM("jam"),
    INQUIRY("inquiry"),
    ICON_GROUP("iconGroup"),
    SLASH("/");

    private final String value;

    FileConstant(final String value) {
        this.value = value;
    }

    public String value(){
        return value;
    }
}
