package com.eventty.userservice.domain.exception;

import com.eventty.userservice.domain.code.ErrorCode;

public class PermissionDeniedException extends UserException {
    public PermissionDeniedException() {
        super(ErrorCode.PERMISSION_DENIED);
    }
}
