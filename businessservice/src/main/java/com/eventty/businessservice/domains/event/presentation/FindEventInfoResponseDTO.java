package com.eventty.businessservice.domains.event.presentation;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FindEventInfoResponseDTO {
    private String image;               // Image(EnCoded Base64)
    private String title;               // 제목 - Event_Basic Table
    private String ticketName;          // 티켓 명(VVIP, VIP...) - Tickets Table
    private Long ticketPrice;           // 티켓 가격 - Tickets Table
    private LocalDateTime eventEndAt;   // 행사 종료 일자 - Event_Basic Table
    private Long eventId;               // 고유 ID
}
