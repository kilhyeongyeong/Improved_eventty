package com.eventty.gateway.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthenticateUserRequestDTO {
    private String accessToken;
    private String refreshToken;
    private String csrfToken;
}
