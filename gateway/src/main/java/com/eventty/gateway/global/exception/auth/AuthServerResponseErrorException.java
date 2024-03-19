package com.eventty.gateway.global.exception.auth;

import com.eventty.gateway.global.exception.ErrorCode;
import com.eventty.gateway.global.exception.GatewayException;

public class AuthServerResponseErrorException extends GatewayException {

    public AuthServerResponseErrorException() { super(ErrorCode.AUTH_SERVER_RESPONSE_ERROR); }

}
