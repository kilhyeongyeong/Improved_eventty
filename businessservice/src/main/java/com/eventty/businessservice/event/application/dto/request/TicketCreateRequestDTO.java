package com.eventty.businessservice.event.application.dto.request;

import com.eventty.businessservice.event.domain.entity.TicketEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TicketCreateRequestDTO {
    @NotBlank
    private String name;

    @NotBlank
    private Long price;

    @NotBlank
    private Long quantity;

    public TicketEntity toEntity(Long eventId){
        return TicketEntity.builder()
                .eventId(eventId)
                .name(name)
                .price(price)
                .quantity(quantity)
                .is_deleted(false)
                .build();
    }
}