package com.eventty.authservice.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GoogleTokenResponseDTO {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private String expiresIn;

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("token_type")
    private String tokenType;
    // private String idToken;
}
