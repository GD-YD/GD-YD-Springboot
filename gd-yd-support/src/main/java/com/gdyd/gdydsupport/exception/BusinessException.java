package com.gdyd.gdydsupport.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    private final String message;

    public BusinessException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

    public BusinessException(ErrorCode errorCode, Object... args) {
        this.errorCode = errorCode;
        this.message = String.format(errorCode.getMessage(), args);
    }
}
