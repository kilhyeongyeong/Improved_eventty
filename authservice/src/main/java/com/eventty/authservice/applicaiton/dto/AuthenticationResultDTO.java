package com.eventty.authservice.applicaiton.dto;

import com.eventty.authservice.domain.entity.AuthUserEntity;

public record AuthenticationResultDTO(
        AuthUserEntity AuthUserEntity,
        boolean needsUpate
) {
}
