package com.eventty.applyservice.application.dto.response;

import lombok.*;

@Getter @Setter @ToString @Builder
@NoArgsConstructor @AllArgsConstructor
public class FindUsingTicketResponseDTO {
    private Long ticketId;
    private Long appliedTicketCount;
}
