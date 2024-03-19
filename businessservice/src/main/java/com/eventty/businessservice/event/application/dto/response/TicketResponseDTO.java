package com.eventty.businessservice.event.application.dto.response;

import com.eventty.businessservice.event.api.dto.response.QueryAppliesCountResponseDTO;
import com.eventty.businessservice.event.domain.entity.TicketEntity;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class TicketResponseDTO {
    private Long id;
    private String name;
    private Long price;
    private Long quantity; // 티켓 고정 수량
    private Long eventId;

    private Long appliedTicketCount; // 해당 티켓을 신청한 수량 (from Apply Server)

    public static TicketResponseDTO from(TicketEntity ticketEntity){
        return TicketResponseDTO.builder()
                .id(ticketEntity.getId())
                .name(ticketEntity.getName())
                .price(ticketEntity.getPrice())
                .quantity(ticketEntity.getQuantity())
                .eventId(ticketEntity.getEventId())
                .build();
    }

    public static TicketResponseDTO from(TicketEntity ticketEntity, QueryAppliesCountResponseDTO appliesInfo){
        return TicketResponseDTO.builder()
                .id(ticketEntity.getId())
                .name(ticketEntity.getName())
                .price(ticketEntity.getPrice())
                .quantity(ticketEntity.getQuantity())
                .eventId(ticketEntity.getEventId())
                .appliedTicketCount(appliesInfo == null ? 0L : appliesInfo.getAppliedTicketCount()) // 신청된 티켓 갯수 정보가 없으면 0으로 반환
                .build();
    }
}
