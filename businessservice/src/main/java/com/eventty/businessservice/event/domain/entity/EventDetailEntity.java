package com.eventty.businessservice.event.domain.entity;

import com.eventty.businessservice.event.application.dto.request.EventUpdateRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class EventDetailEntity {
    private Long id;
    private String content;
    private LocalDateTime applyStartAt;
    private LocalDateTime applyEndAt;
    private Long views;
    private LocalDateTime deleteDate;
    private LocalDateTime updateDate;
    private LocalDateTime createDate;

    public void updateEventDetail(EventUpdateRequestDTO request) {
        this.content = request.getContent() == null ? this.content : request.getContent();
        this.applyStartAt = request.getApplyStartAt() == null ? this.applyStartAt : request.getApplyStartAt();
        this.applyEndAt = request.getApplyEndAt() == null ? this.applyEndAt : request.getApplyEndAt();
    }
}
