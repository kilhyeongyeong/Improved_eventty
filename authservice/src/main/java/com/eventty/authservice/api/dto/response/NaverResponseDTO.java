package com.eventty.authservice.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NaverResponseDTO {
    @JsonProperty("resultcode")
    private String resultCode;

    @JsonProperty("message")
    private String message;

    @JsonProperty("response")
    private NaverUserInfoResponseDTO response;
}
