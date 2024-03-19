package com.eventty.businessservice.event.domain.entity;

import com.eventty.businessservice.event.application.dto.request.TicketUpdateRequestDTO;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TicketEntity {
    private Long id;
    private String name;
    private Long price;
    private Long quantity; // 티켓 수량
    private Long eventId;
    private Boolean is_deleted;

    public void update(TicketUpdateRequestDTO ticketUpdateRequestDTO){
        this.name = ticketUpdateRequestDTO.getName() == null ? this.name : ticketUpdateRequestDTO.getName();
        this.price = ticketUpdateRequestDTO.getPrice() == null ? this.price : ticketUpdateRequestDTO.getPrice();
        this.quantity = ticketUpdateRequestDTO.getQuantity() == null ? this.quantity : ticketUpdateRequestDTO.getQuantity();
    }
}
