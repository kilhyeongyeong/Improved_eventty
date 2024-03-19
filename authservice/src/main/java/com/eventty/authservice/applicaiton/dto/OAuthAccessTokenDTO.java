package com.eventty.authservice.applicaiton.dto;

public record OAuthAccessTokenDTO (
        String accessToken,
        String tokenType
) {}
