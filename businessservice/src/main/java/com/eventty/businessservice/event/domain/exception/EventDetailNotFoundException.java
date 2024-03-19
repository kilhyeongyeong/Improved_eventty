package com.eventty.businessservice.event.domain.exception;

import com.eventty.businessservice.event.domain.Enum.ErrorCode;


public class EventDetailNotFoundException extends BusinessException {
    public final static BusinessException EXCEPTION = new EventDetailNotFoundException();
    private EventDetailNotFoundException(){
        super(ErrorCode.EVENT_NOT_FOUND);
    }
}