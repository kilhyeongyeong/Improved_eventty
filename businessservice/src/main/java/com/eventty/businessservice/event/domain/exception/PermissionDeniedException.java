package com.eventty.businessservice.event.domain.exception;

import com.eventty.businessservice.event.domain.Enum.ErrorCode;

public class PermissionDeniedException extends BusinessException {
    public final static BusinessException EXCEPTION = new PermissionDeniedException();
    public PermissionDeniedException() {
        super(ErrorCode.PERMISSION_DENIED);
    }
}
