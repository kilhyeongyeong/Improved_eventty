package com.eventty.applyservice.domain.exception;

import com.eventty.applyservice.domain.code.ErrorCode;

public class ExceedApplicantsException extends ApplyException{
    private static final String[] fields = {"currParticipateNum"};
    public ExceedApplicantsException(Long currParticipateNum) {
        super(ErrorCode.EXCEED_APPLICANTS, currParticipateNum, fields);
    }
}
