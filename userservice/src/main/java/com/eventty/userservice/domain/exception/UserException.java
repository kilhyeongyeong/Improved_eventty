package com.eventty.userservice.domain.exception;

import com.eventty.userservice.domain.code.ErrorCode;
import lombok.Getter;

import java.lang.reflect.Field;

@Getter
public class UserException extends RuntimeException{
    private ErrorCode errorCode;
    private Object causedErrorData;
    private String[] fields;

    protected UserException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    protected UserException(ErrorCode errorCode, Object causedErrorData) {
        this.errorCode = errorCode;
        this.causedErrorData = causedErrorData;
    }

    protected UserException(ErrorCode errorCode, Object causedErrorData, String[] fields) {
        this.errorCode = errorCode;
        this.causedErrorData = causedErrorData;
        this.fields = fields;
    }
}
