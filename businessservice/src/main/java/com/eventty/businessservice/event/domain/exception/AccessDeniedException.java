package com.eventty.businessservice.event.domain.exception;

import com.eventty.businessservice.event.domain.Enum.ErrorCode;

public class AccessDeniedException extends BusinessException {
    public final static BusinessException EXCEPTION = new AccessDeniedException();
    public AccessDeniedException() {
        super(ErrorCode.ACCESS_DENIED);
    }
}
