package com.timeToast.timeToast.global.exception;

import com.timeToast.timeToast.global.constant.StatusCode;

public class InternalServerException extends RuntimeException{
    private final StatusCode statusCode = StatusCode.INTERNAL_SERVER_ERROR;
    private final String message;

    public InternalServerException(final String message){
        this.message = message;
    }
}
