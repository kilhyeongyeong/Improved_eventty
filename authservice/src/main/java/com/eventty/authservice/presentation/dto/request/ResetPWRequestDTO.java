package com.eventty.authservice.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResetPWRequestDTO {
    @NotNull
    String password;
}
