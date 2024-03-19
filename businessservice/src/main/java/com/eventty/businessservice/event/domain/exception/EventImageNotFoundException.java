package com.eventty.businessservice.event.domain.exception;

import com.eventty.businessservice.event.domain.Enum.ErrorCode;

public class EventImageNotFoundException extends BusinessException {
    public final static BusinessException EXCEPTION = new EventImageNotFoundException();
    private EventImageNotFoundException(){
        super(ErrorCode.DATA_NOT_FOUND);
    }
}

