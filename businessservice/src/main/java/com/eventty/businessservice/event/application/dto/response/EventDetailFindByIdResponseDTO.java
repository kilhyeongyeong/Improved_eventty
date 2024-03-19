package com.eventty.businessservice.event.application.dto.response;

import com.eventty.businessservice.event.domain.entity.EventDetailEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class EventDetailFindByIdResponseDTO {
    private Long id;
    private String content;
    private LocalDateTime applyStartAt;
    private LocalDateTime applyEndAt;
    private Long views;
    private LocalDateTime deleteDate;
    private LocalDateTime updateDate;
    private LocalDateTime createDate;

    public static EventDetailFindByIdResponseDTO from(EventDetailEntity eventDetailEntity){
        return EventDetailFindByIdResponseDTO.builder()
                .id(eventDetailEntity.getId())
                .content(eventDetailEntity.getContent())
                .applyStartAt(eventDetailEntity.getApplyStartAt())
                .applyEndAt(eventDetailEntity.getApplyEndAt())
                .views(eventDetailEntity.getViews())
                .deleteDate(eventDetailEntity.getDeleteDate())
                .updateDate(eventDetailEntity.getUpdateDate())
                .createDate(eventDetailEntity.getCreateDate())
                .build();
    }
}
