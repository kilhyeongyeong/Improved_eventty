package com.eventty.authservice.presentation.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserLoginRequestDTO {
        @NotNull @Email
        private String email;

        @NotNull
        private String password;
}
