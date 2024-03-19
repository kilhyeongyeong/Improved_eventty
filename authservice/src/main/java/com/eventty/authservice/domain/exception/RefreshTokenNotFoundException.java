package com.eventty.authservice.domain.exception;

import com.eventty.authservice.global.Enum.ErrorCode;
import com.eventty.authservice.global.exception.AuthException;

public class RefreshTokenNotFoundException extends AuthException {
    private static final String[] fields = {"userId"};
    public RefreshTokenNotFoundException(Long userId) {
        super(ErrorCode.REFRESH_TOKEN_NOT_FOUND, userId, fields);
    }
}
