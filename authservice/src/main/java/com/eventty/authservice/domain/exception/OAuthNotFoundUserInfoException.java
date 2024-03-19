package com.eventty.authservice.domain.exception;

import com.eventty.authservice.global.Enum.ErrorCode;
import com.eventty.authservice.global.exception.AuthException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OAuthNotFoundUserInfoException extends AuthException {
    public OAuthNotFoundUserInfoException(String socialName) {
        super(ErrorCode.OAUTH_NOT_FOUND_USER_INFO);
        log.error("Social Service Name: {}", socialName);
    }
}
