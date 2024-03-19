package com.eventty.authservice.domain.exception;

import com.eventty.authservice.global.Enum.ErrorCode;
import com.eventty.authservice.global.exception.AuthException;

public class PermissionDeniedException extends AuthException {
    public PermissionDeniedException() {
        super(ErrorCode.PERMISSION_DENIED);
    }
}
