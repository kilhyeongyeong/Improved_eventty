package com.eventty.applyservice.domain.exception;

import com.eventty.applyservice.domain.code.ErrorCode;

public class AlreadyCancelApplyException extends ApplyException{
    public AlreadyCancelApplyException() {
        super(ErrorCode.ALREADY_CANCELED_APPLY);
    }
}
