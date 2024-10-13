package com.timeToast.timeToast.global.exception;

import com.timeToast.timeToast.global.constant.StatusCode;

public class BadRequestException extends RuntimeException{
    private final StatusCode statusCode = StatusCode.BAD_REQUEST;
    private final String message;

    public BadRequestException(final String message){
        this.message = message;
    }
}
