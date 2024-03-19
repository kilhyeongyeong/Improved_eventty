package com.eventty.applyservice.application.dto.request;

import lombok.*;

@Getter @Setter @Builder @ToString
@AllArgsConstructor @NoArgsConstructor
public class FindEventInfoRequestDTO {
    private Long eventId;
    private Long ticketId;
}
