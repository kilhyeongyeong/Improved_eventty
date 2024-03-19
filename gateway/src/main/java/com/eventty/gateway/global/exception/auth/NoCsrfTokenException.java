package com.eventty.gateway.global.exception.auth;

import com.eventty.gateway.global.exception.ErrorCode;
import com.eventty.gateway.global.exception.GatewayException;

public class NoCsrfTokenException extends GatewayException {
    public NoCsrfTokenException() {
        super(ErrorCode.NO_CSRF_TOKEN);
    }
}
