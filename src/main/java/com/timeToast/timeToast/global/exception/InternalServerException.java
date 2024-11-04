package com.timeToast.timeToast.global.exception;

import com.timeToast.timeToast.global.constant.StatusCode;
import lombok.Getter;

@Getter
public class InternalServerException extends RuntimeException{
    private final String statusCode = StatusCode.INTERNAL_SERVER_ERROR.getStatusCode();
    private final String message;

    public InternalServerException(final String message){
        this.message = message;
    }
}
