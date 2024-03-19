package com.eventty.applyservice.domain.exception;

import com.eventty.applyservice.domain.code.ErrorCode;

public class NonExistentIdException extends ApplyException{

    public NonExistentIdException() {
        super(ErrorCode.NON_EXISTENT_ID);
    }
}
