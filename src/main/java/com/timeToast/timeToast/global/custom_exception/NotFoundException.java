package com.timeToast.timeToast.global.custom_exception;

import com.timeToast.timeToast.global.constant.StatusCode;
import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException{

    private final StatusCode statusCode = StatusCode.NOT_FOUND;
    private final String message;

    public NotFoundException(final String message){
        this.message = message;
    }
}
