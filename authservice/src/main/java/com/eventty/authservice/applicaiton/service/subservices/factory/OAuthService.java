package com.eventty.authservice.applicaiton.service.subservices.factory;

import com.eventty.authservice.applicaiton.dto.OAuthAccessTokenDTO;
import com.eventty.authservice.applicaiton.dto.OAuthUserInfoDTO;
import com.eventty.authservice.domain.entity.OAuthUserEntity;
import com.eventty.authservice.presentation.dto.request.OAuthLoginRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Optional;

public interface OAuthService {
    OAuthAccessTokenDTO getToken(OAuthLoginRequestDTO oAuthLoginRequestDTO);
    OAuthUserInfoDTO getUserInfo(OAuthAccessTokenDTO oAuthAccessTokenDTO);
    Optional<OAuthUserEntity> findOAuthUserEntity(String clientId);
    Long create(OAuthUserEntity oAuthUserEntity);
}
