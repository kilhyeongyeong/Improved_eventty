package com.eventty.authservice.presentation.dto.request;

import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FullUserCreateRequestDTO{
        @NotNull @Email
        private String email;
        @NotNull
        private String password;
        @NotNull
        private String name;
        @NotNull
        private String phone;
        private LocalDate birth;
        private String address;
}
