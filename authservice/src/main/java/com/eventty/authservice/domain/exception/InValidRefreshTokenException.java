package com.eventty.authservice.domain.exception;

import com.eventty.authservice.applicaiton.dto.ValidateRefreshTokenDTO;
import com.eventty.authservice.global.Enum.ErrorCode;
import com.eventty.authservice.global.exception.AuthException;

public class InValidRefreshTokenException extends AuthException {
    private static final String[] fields = {"userId", "refreshToken"};
    public InValidRefreshTokenException(ValidateRefreshTokenDTO validateRefreshTokenDTO) {
        super(ErrorCode.INVALID_REFRESH_TOKEN, validateRefreshTokenDTO, fields);
    }
}