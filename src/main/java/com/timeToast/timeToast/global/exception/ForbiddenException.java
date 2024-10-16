package com.timeToast.timeToast.global.exception;

import com.timeToast.timeToast.global.constant.StatusCode;

public class ForbiddenException extends RuntimeException{
    private final StatusCode statusCode = StatusCode.FORBIDDEN;
    private final String message;

    public ForbiddenException(final String message){
        this.message = message;
    }
}
