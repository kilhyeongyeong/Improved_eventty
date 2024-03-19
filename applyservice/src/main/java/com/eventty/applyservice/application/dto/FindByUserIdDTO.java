package com.eventty.applyservice.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString @Builder
@NoArgsConstructor @AllArgsConstructor
public class FindByUserIdDTO {
    private Long eventId;
    private Long ticketId;
    private Long applicantNum;
    private String phone;
    private LocalDateTime applyDate;
    private LocalDateTime deleteDate;
    private Long applyId;
}
