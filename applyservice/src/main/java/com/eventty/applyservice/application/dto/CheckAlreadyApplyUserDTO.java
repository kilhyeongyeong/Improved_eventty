package com.eventty.applyservice.application.dto;

import lombok.*;

@Getter @Setter @Builder @ToString
@AllArgsConstructor @NoArgsConstructor
public class CheckAlreadyApplyUserDTO {
    private Long userId;
    private Long eventId;
}
