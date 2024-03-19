package com.eventty.authservice.applicaiton.dto;

public record ValidateRefreshTokenDTO(
        Long userId,
        String refreshToken
) {
}
