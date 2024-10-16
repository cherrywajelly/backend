package com.timeToast.timeToast.global.exception;

import com.timeToast.timeToast.global.constant.StatusCode;

public class ConflictException extends RuntimeException{
    private final StatusCode statusCode = StatusCode.CONFLICT;
    private final String message;

    public ConflictException(final String message){
        this.message = message;
    }
}
