package com.eventty.authservice.domain.exception;

import com.eventty.authservice.global.Enum.ErrorCode;
import com.eventty.authservice.global.exception.AuthException;

public class UserNotFoundException extends AuthException {

    public UserNotFoundException(String value, String fieldName) {
        super(ErrorCode.USER_NOT_FOUND, value, new String[]{fieldName});
    }

    public UserNotFoundException(Long userId) {
        super(ErrorCode.USER_NOT_FOUND, userId, new String[]{"userId"});
    }
}