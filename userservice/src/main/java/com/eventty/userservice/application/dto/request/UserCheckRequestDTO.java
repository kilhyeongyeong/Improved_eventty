package com.eventty.userservice.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @ToString @Builder
@AllArgsConstructor
public class UserCheckRequestDTO {
    @NotBlank(message = "a null value or '' value or ' ' value")
    private String phone;
    @NotBlank(message = "a null value or '' value or ' ' value")
    private String name;
}
