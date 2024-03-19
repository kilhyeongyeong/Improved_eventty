package com.eventty.applyservice.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString @Builder
@NoArgsConstructor @AllArgsConstructor
public class FindApplicantsListDTO {
    private Long applyId;
    private String phone;
    private Long applicantNum;
    private LocalDateTime date;
    private Long userId;
    private String name;
    private Long ticketId;
    private LocalDateTime deleteDate;
    private String state;
}
