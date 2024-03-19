package com.eventty.businessservice.domains.event.presentation;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FindEventInfoRequestDTO {
    private Long ticketIds;
}