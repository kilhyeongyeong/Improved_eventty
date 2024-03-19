package com.eventty.businessservice.event.application.dto.response;

import lombok.*;

import java.time.LocalDateTime;

/**
 * 다른 서버에게 전달할 DTO (From Here, To Other Servers)
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class EventInfoApiResponseDTO {
    private String image;               // Image(Image Path)
    private String title;               // 제목 - Event_Basic Table
    private Long ticketId;              // 티켓 고유 ID - Tickets Table
    private String ticketName;          // 티켓 명(VVIP, VIP...) - Tickets Table
    private Long ticketPrice;           // 티켓 가격 - Tickets Table
    private LocalDateTime eventEndAt;   // 행사 종료 일자 - Event_Basic Table
    private Long eventId;               // Event_Basic 고유 ID

    public static EventInfoApiResponseDTO from(
            ImageResponseDTO image, EventBasicWithoutHostInfoResponseDTO eventBasic, TicketResponseDTO ticket
    ) {
        return EventInfoApiResponseDTO.builder()
                .image(image.getImagePathFromStorage())
                .title(eventBasic.getTitle())
                .ticketId(ticket.getId())
                .ticketName(ticket.getName())
                .ticketPrice(ticket.getPrice())
                .eventEndAt(eventBasic.getEventEndAt())
                .eventId(eventBasic.getId())
                .build();
    }

    // Swagger 을 위하여 기본 생성자로 기본값 설정
    public EventInfoApiResponseDTO() {
        this.image = "https://eventty-storage.s3.ap-northeast-2.amazonaws.com/2021/08/15/1629025200_1.jpg";
        this.title = "Test Event 1";
        this.ticketId = 1L;
        this.ticketName = "VVIP";
        this.ticketPrice = 100000L;
        this.eventEndAt = LocalDateTime.now();
        this.eventId = 1L;
    }
}
