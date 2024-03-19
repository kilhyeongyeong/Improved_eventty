package com.eventty.applyservice.domain.exception;

import com.eventty.applyservice.application.dto.CheckAlreadyApplyUserDTO;
import com.eventty.applyservice.domain.code.ErrorCode;

public class AlreadyApplyUserException extends ApplyException{
    private static final String[] fields = {"userId", "eventId"};

    public AlreadyApplyUserException(CheckAlreadyApplyUserDTO checkAlreadyApplyUserDTO){
        super(ErrorCode.ALREADY_APPLY_USER, checkAlreadyApplyUserDTO, fields);
    }
}
