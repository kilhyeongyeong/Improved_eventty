package com.eventty.businessservice.event.application.dto.request;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TicketUpdateRequestDTO {
    private Long id;

    private String name;

    @PositiveOrZero
    private Long price;

    @PositiveOrZero
    private Long quantity; // 티켓 수량
}