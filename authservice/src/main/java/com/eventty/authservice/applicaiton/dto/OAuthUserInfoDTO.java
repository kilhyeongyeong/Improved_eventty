package com.eventty.authservice.applicaiton.dto;

import java.time.LocalDate;

public record OAuthUserInfoDTO (
        String clientId,
        String email,
        String name,
        LocalDate birth,
        String phone,
        String picture
){ }
