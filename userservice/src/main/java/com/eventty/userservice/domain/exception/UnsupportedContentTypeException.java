package com.eventty.userservice.domain.exception;

import com.eventty.userservice.domain.code.ErrorCode;

public class UnsupportedContentTypeException extends UserException{
    public UnsupportedContentTypeException(Object causedErrorData) {
        super(ErrorCode.CONTENTTYPE_ERROR, causedErrorData, new String[]{"contentType"});
    }
}
