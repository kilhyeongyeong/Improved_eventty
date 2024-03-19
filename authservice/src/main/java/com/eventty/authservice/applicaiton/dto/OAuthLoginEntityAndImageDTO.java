package com.eventty.authservice.applicaiton.dto;

import com.eventty.authservice.api.dto.response.ImageQueryApiResponseDTO;
import com.eventty.authservice.domain.entity.AuthUserEntity;

public record OAuthLoginEntityAndImageDTO (
        AuthUserEntity authUserEntity,
        ImageQueryApiResponseDTO imageQueryApiResponseDTO
) { }
