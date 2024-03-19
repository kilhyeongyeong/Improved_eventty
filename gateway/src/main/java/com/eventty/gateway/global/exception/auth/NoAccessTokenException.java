package com.eventty.gateway.global.exception.auth;

import com.eventty.gateway.global.exception.ErrorCode;
import com.eventty.gateway.global.exception.GatewayException;

public class NoAccessTokenException  extends GatewayException {
    public NoAccessTokenException() { super(ErrorCode.NO_ACCESS_TOKEN); }
}
