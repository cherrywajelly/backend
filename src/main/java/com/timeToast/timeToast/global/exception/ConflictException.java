package com.timeToast.timeToast.global.exception;

import com.timeToast.timeToast.global.constant.StatusCode;
import lombok.Getter;

@Getter
public class ConflictException extends RuntimeException{
    private final String statusCode = StatusCode.CONFLICT.getStatusCode();
    private final String message;

    public ConflictException(final String message){
        this.message = message;
    }
}
