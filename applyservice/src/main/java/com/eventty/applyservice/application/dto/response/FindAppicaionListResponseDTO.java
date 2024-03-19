package com.eventty.applyservice.application.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString @Builder
@NoArgsConstructor @AllArgsConstructor
public class FindAppicaionListResponseDTO {
    private String image;               // Image(EnCoded Base64)
    private String title;               // 제목 - Event_Basic Table
    private String ticketName;          // 티켓 명(VVIP, VIP...) - Tickets Table
    private Long ticketPrice;           // 티켓 가격 - Tickets Table
    private LocalDateTime date;         // 신청 일자 또는 취소 일자 - ApplyTable
    private String status;              // 신청상태 - 예약 완료, 예약 취소, 행사 종료
    private Long applyId;               // apply 고유 ID
    private Long applicantNum;          // 함께 신청했던 참여자 수
}
