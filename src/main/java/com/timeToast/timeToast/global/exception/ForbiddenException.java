package com.timeToast.timeToast.global.exception;

import com.timeToast.timeToast.global.constant.StatusCode;
import lombok.Getter;

@Getter
public class ForbiddenException extends RuntimeException{
    private final String statusCode = StatusCode.FORBIDDEN.getStatusCode();
    private final String message;

    public ForbiddenException(final String message){
        this.message = message;
    }
}
