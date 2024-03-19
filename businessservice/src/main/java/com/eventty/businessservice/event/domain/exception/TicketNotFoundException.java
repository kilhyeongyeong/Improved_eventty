package com.eventty.businessservice.event.domain.exception;

import com.eventty.businessservice.event.domain.Enum.ErrorCode;

public class TicketNotFoundException extends BusinessException {
    public final static BusinessException EXCEPTION = new TicketNotFoundException();
    private TicketNotFoundException(){
        super(ErrorCode.TICKET_NOT_FOUND);
    }
}
