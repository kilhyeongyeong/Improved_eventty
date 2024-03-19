package com.eventty.businessservice.event.domain.exception;

import com.eventty.businessservice.event.domain.Enum.ErrorCode;

public class HostInfoNotFoundException extends BusinessException{
    public final static BusinessException EXCEPTION = new HostInfoNotFoundException();
    private HostInfoNotFoundException(){
        super(ErrorCode.HOST_INFO_NOT_FOUND);
    }
}
