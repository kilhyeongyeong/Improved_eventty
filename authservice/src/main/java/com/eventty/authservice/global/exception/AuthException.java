package com.eventty.authservice.global.exception;

import com.eventty.authservice.global.Enum.ErrorCode;
import lombok.Getter;

@Getter
public class AuthException extends RuntimeException{
    private final ErrorCode errorCode;
    private Object causedErrorData;
    private String[] fields;

    protected AuthException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    protected AuthException(ErrorCode errorCode, Object causedErrorData, String[] fields) {
        this.errorCode = errorCode;
        this.causedErrorData = causedErrorData;
        this.fields = fields;
    }
}
