package com.timeToast.timeToast.global.exception;

import com.timeToast.timeToast.global.constant.StatusCode;
import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException{
    private final StatusCode statusCode = StatusCode.UNAUTHORIZED;
    private final String message;

    public UnauthorizedException(final String message){
        this.message = message;
    }
}
