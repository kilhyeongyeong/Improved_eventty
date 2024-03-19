package com.eventty.authservice.domain.exception;

import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.global.Enum.ErrorCode;
import com.eventty.authservice.global.exception.AuthException;

public class AccessDeletedUserException extends AuthException {

    private static final String[] fields = {"id", "email"};
    public AccessDeletedUserException(AuthUserEntity AuthUserEntity) {
        super(ErrorCode.ACCESS_DELETED_USER, AuthUserEntity, fields);
    }
}
