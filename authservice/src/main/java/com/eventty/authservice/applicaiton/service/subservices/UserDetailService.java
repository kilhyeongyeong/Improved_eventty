package com.eventty.authservice.applicaiton.service.subservices;

import com.eventty.authservice.domain.Enum.UserRole;
import com.eventty.authservice.domain.entity.AuthUserEntity;

import java.util.List;

public interface UserDetailService {

    Long delete(AuthUserEntity AuthUserEntity);
    AuthUserEntity create(AuthUserEntity AuthUserEntity, UserRole userRole);

    void validateEmail(String email);
    void validationUser(AuthUserEntity AuthUserEntity);

    AuthUserEntity findAuthUser(String email);

    AuthUserEntity findAuthUser(Long userId);
    AuthUserEntity changePwAuthUser(String encryptedPassword, AuthUserEntity AuthUserEntity);
    List<AuthUserEntity> findNotDeletedAuthUserList(List<Long> userIds);
}
