package com.eventty.authservice.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OAuthLoginRequestDTO {
    private String code;
}
