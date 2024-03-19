package com.eventty.businessservice.event.domain.entity;

import com.eventty.businessservice.event.application.dto.request.EventUpdateRequestDTO;
import com.eventty.businessservice.event.domain.Enum.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class
EventBasicEntity {
    private Long id;
    private Long hostId;
    private String title;
    private LocalDateTime eventStartAt;
    private LocalDateTime eventEndAt;
    private Long participateNum;
    private String location;
    private Long category;
    private Boolean isActive;
    private Boolean isDeleted;

    public void subtractParticipateNum(Long participateNumPerTicket){
        this.participateNum -= participateNumPerTicket;
    }
    public void updateEventBasic(EventUpdateRequestDTO request){
        this.title = request.getTitle() == null ? this.title : request.getTitle();
        this.eventStartAt = request.getEventStartAt() == null ? this.eventStartAt : request.getEventStartAt();
        this.eventEndAt = request.getEventEndAt() == null ? this.eventEndAt : request.getEventEndAt();
        this.participateNum = request.getParticipateNum() == null ? this.participateNum : request.getParticipateNum();
        this.location = request.getLocation() == null ? this.location : request.getLocation();
        this.category = request.getCategory() == null ? this.category : Category.valueOf(request.getCategory()).getId();
        this.isActive = request.getIsActive() == null ? this.isActive : request.getIsActive();
    }
}
