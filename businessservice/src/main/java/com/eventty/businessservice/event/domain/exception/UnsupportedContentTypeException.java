package com.eventty.businessservice.event.domain.exception;

import com.eventty.businessservice.event.domain.Enum.ErrorCode;

public class UnsupportedContentTypeException extends BusinessException{

    public final static BusinessException EXCEPTION = new UnsupportedContentTypeException();
    private UnsupportedContentTypeException() {
        super(ErrorCode.CONTENT_TYPE_ERROR);
    }

}
