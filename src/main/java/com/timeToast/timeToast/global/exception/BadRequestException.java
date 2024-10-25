package com.timeToast.timeToast.global.exception;

import com.timeToast.timeToast.global.constant.StatusCode;
import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException{
    private final String statusCode = StatusCode.BAD_REQUEST.getStatusCode();
    private final String message;

    public BadRequestException(final String message){
        this.message = message;
    }
}
