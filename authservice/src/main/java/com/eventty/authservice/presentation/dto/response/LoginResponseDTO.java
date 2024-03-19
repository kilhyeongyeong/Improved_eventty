package com.eventty.authservice.presentation.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginResponseDTO {
    private Long userId;
    private String email;
    private String role;
    private String imageName;
    private String imagePath;
}