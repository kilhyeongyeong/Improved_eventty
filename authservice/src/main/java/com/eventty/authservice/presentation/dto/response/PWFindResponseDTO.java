package com.eventty.authservice.presentation.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PWFindResponseDTO {
    private Long userId;
    private String email;
}
