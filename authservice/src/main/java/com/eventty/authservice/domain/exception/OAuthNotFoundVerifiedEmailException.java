package com.eventty.authservice.domain.exception;

import com.eventty.authservice.global.Enum.ErrorCode;
import com.eventty.authservice.global.exception.AuthException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OAuthNotFoundVerifiedEmailException extends AuthException {
    public OAuthNotFoundVerifiedEmailException(String socialName) {
        super(ErrorCode.OAUTH_NOT_FOUND_VERIFIED_EMAIL);
        log.error("Social Service Name: {}", socialName);
    }
}
