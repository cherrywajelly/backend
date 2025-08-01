package com.timeToast.timeToast.domain.enums.member;

import com.timeToast.timeToast.global.exception.UnauthorizedException;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.INVALID_TOKEN_FORMAT;

public enum MemberRole {

    USER, MANAGER, CREATOR, STAFF;

    public static MemberRole from(String value) {
        try {
            return MemberRole.valueOf(value.toUpperCase());
        } catch (Exception e) {
            System.out.println(value);
            throw new UnauthorizedException(INVALID_TOKEN_FORMAT.getMessage());
        }
    }
}
