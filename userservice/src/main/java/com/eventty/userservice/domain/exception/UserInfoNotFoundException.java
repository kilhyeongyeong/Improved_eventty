package com.eventty.userservice.domain.exception;

import com.eventty.userservice.domain.UserEntity;
import com.eventty.userservice.domain.code.ErrorCode;

public class UserInfoNotFoundException extends UserException {
    public UserInfoNotFoundException(Long id){
        super(ErrorCode.USER_INFO_NOT_FOUND, id, new String[]{"userId"});
    }
    public UserInfoNotFoundException(Object obj,String[] fields){
        super(ErrorCode.USER_INFO_NOT_FOUND, obj, fields);
    }
}