package com.eventty.businessservice.event.domain.exception;

import com.eventty.businessservice.event.domain.Enum.ErrorCode;

public class EventNotFoundException extends BusinessException {
    public final static BusinessException EXCEPTION = new EventNotFoundException();
    private EventNotFoundException(){
        super(ErrorCode.EVENT_NOT_FOUND);
    }
}
