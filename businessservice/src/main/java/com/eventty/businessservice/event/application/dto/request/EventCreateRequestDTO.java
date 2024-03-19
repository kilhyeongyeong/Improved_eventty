package com.eventty.businessservice.event.application.dto.request;

import com.eventty.businessservice.event.domain.Enum.Category;
import com.eventty.businessservice.event.domain.entity.EventDetailEntity;
import com.eventty.businessservice.event.domain.entity.EventBasicEntity;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class EventCreateRequestDTO {
    @NotNull
    private Long userId;

    @NotBlank
    private String title;

    @FutureOrPresent
    private LocalDateTime eventStartAt;

    @FutureOrPresent
    private LocalDateTime eventEndAt;

    private Long participateNum;

    @NotBlank
    private String location;

    private String category;

    private Boolean isActive;
    private Boolean isDeleted;

    @NotBlank
    private String content;

    @FutureOrPresent
    private LocalDateTime applyStartAt;

    @FutureOrPresent
    private LocalDateTime applyEndAt;

    private List<TicketCreateRequestDTO> tickets;

    public EventBasicEntity toEventBasicEntity() {
        return EventBasicEntity.builder()
                .hostId(userId)
                .title(title)
                .eventStartAt(eventStartAt)
                .eventEndAt(eventEndAt)
                .participateNum(participateNum)
                .location(location)
                .category(Category.valueOf(category).getId())
                .isActive(true)
                .isDeleted(false)
                .build();
    }

    public EventDetailEntity toEventDetailEntity(Long eventId) {
        return EventDetailEntity.builder()
                .id(eventId)
                .content(content)
                .applyStartAt(applyStartAt)
                .applyEndAt(applyEndAt)
                .createDate(LocalDateTime.now())
                .build();
    }


}
