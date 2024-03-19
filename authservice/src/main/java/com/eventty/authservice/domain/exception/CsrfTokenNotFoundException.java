package com.eventty.authservice.domain.exception;

import com.eventty.authservice.global.Enum.ErrorCode;
import com.eventty.authservice.global.exception.AuthException;

public class CsrfTokenNotFoundException extends AuthException {
    private static final String[] fields = {"userId"};
    public CsrfTokenNotFoundException(Long userId) {
        super(ErrorCode.CSRF_TOKEN_NOT_FOUND, userId, fields);
    }
}

