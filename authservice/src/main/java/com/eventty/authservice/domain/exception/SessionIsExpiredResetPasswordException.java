package com.eventty.authservice.domain.exception;

import com.eventty.authservice.global.Enum.ErrorCode;
import com.eventty.authservice.global.exception.AuthException;

public class SessionIsExpiredResetPasswordException extends AuthException {
    public SessionIsExpiredResetPasswordException() {
        super(ErrorCode.SESSION_IS_EXPIRED_RESET_PASSWORD);
    }
}
