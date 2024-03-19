package com.eventty.businessservice.event.api.dto.response;

import lombok.*;

/**
 * Apply Server 와의 통신 결과로 받는 DTO (From Apply Server, To Here)
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QueryAppliesCountResponseDTO {
    Long ticketId;
    Long appliedTicketCount; // 예약된 티켓 수
}