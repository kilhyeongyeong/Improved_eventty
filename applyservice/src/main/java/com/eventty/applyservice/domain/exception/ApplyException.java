package com.eventty.applyservice.domain.exception;

import com.eventty.applyservice.domain.code.ErrorCode;
import lombok.Getter;

@Getter
public class ApplyException extends RuntimeException{
    private ErrorCode errorCode;
    private Object causedErrorData;
    private String[] fields;

    protected ApplyException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    protected ApplyException(ErrorCode errorCode, Object causedErrorData) {
        this.errorCode = errorCode;
        this.causedErrorData = causedErrorData;
    }

    protected ApplyException(ErrorCode errorCode, Object causedErrorData, String[] fields) {
        this.errorCode = errorCode;
        this.causedErrorData = causedErrorData;
        this.fields = fields;
    }
}
