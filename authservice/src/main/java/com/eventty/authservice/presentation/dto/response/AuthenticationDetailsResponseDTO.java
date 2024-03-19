package com.eventty.authservice.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthenticationDetailsResponseDTO {
    private Long userId;
    private String accessToken;
    private String refreshToken;
    private String csrfToken;
    private String authoritiesJSON;
    private boolean needsUpdate;
}
