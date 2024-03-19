package com.eventty.authservice.domain.exception;

import com.eventty.authservice.global.Enum.ErrorCode;
import com.eventty.authservice.global.exception.AuthException;
import com.eventty.authservice.presentation.dto.request.UserLoginRequestDTO;

public class InvalidPasswordException extends AuthException {

    private static final String[] fields = {"email"};
    public InvalidPasswordException(UserLoginRequestDTO userLoginRequestDTO) {
        super(ErrorCode.INVALID_PASSWORD, userLoginRequestDTO, fields);
    }
}