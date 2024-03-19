package com.eventty.gateway.global.exception;

import lombok.Getter;

@Getter
public class GatewayException extends RuntimeException {

    private ErrorCode errorCode;
    private Object causedErrorData;
    private String[] fields;

    protected GatewayException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    protected GatewayException(ErrorCode errorCode, Object cuasedErrorData, String[] fields) {
        this.errorCode = errorCode;
        this.causedErrorData = cuasedErrorData;
        this.fields = fields;
    }
}
