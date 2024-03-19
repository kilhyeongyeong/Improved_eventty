package com.eventty.applyservice.application.dto.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @Builder @ToString
@AllArgsConstructor @NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class FindEventInfoResponseDTO {
    private String image;               // Image(EnCoded Base64)
    private String title;               // 제목 - Event_Basic Table
    private String ticketName;          // 티켓 명(VVIP, VIP...) - Tickets Table
    private Long ticketPrice;           // 티켓 가격 - Tickets Table
    private LocalDateTime eventEndAt;   // 행사 종료 일자 - Event_Basic Table
    private Long eventId;               // Event_Basic 고유 ID
    private Long ticketId;
}
