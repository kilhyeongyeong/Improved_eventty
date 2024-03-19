package com.eventty.applyservice.domain.exception;

import com.eventty.applyservice.domain.code.ErrorCode;

public class PermissionDeniedException extends ApplyException {
    public PermissionDeniedException() {
        super(ErrorCode.PERMISSION_DENIED);
    }
}
