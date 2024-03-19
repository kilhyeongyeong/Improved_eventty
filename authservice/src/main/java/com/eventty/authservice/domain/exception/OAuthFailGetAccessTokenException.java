package com.eventty.authservice.domain.exception;

import com.eventty.authservice.global.Enum.ErrorCode;
import com.eventty.authservice.global.exception.AuthException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OAuthFailGetAccessTokenException extends AuthException {
    public OAuthFailGetAccessTokenException(String socialName) {
        super(ErrorCode.OAUTH_FAIL_GET_ACCESS_TOKEN);
        log.error("Social Service Name: {}", socialName);
    }
}
