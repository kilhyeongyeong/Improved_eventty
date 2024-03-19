package com.eventty.applyservice.application.dto;

import lombok.*;

@Getter @Setter @Builder @ToString
@AllArgsConstructor @NoArgsConstructor
public class CreateApplyDTO {
    private Long userId;
    private Long eventId;
    private Long ticketId;
    private Long applicantNum;
    private String phone;
    private String name;
}
