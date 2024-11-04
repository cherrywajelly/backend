package com.timeToast.timeToast.global.exception;

import com.timeToast.timeToast.global.constant.StatusCode;
import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException{

    private final String statusCode = StatusCode.NOT_FOUND.getStatusCode();
    private final String message;

    public NotFoundException(final String message){
        this.message = message;
    }
}
