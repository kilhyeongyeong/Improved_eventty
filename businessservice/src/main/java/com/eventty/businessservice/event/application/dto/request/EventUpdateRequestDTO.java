package com.eventty.businessservice.event.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Builder
public class EventUpdateRequestDTO {
    // Event
    private String title;
    private LocalDateTime eventStartAt;
    private LocalDateTime eventEndAt;
    private String location;
    private String category;
    private Boolean isActive;
    private Long participateNum;

    // EventDetail
    private String content;
    private LocalDateTime applyStartAt;
    private LocalDateTime applyEndAt;

    // Ticket
    private List<TicketUpdateRequestDTO> ticketList;

}
