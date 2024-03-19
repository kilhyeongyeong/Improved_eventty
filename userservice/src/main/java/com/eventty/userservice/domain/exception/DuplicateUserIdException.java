package com.eventty.userservice.domain.exception;

import com.eventty.userservice.domain.UserEntity;
import com.eventty.userservice.domain.code.ErrorCode;

public class DuplicateUserIdException extends UserException{
    public DuplicateUserIdException(UserEntity userEntity){
        super(ErrorCode.USER_ID_DUPLICATE, userEntity, new String[]{"userId"});
    }
}
