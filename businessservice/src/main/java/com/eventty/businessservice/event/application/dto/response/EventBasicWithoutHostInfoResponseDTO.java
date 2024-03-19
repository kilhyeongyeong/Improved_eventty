package com.eventty.businessservice.event.application.dto.response;

import com.eventty.businessservice.event.domain.Enum.Category;
import com.eventty.businessservice.event.domain.entity.EventBasicEntity;
import lombok.*;

import java.time.LocalDateTime;

// 이벤트 메인페이지에서 사용할 DTO (Host 정보 미포함)
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class EventBasicWithoutHostInfoResponseDTO {
    private Long id;
    private Long hostId;
    private String title;
    private LocalDateTime eventStartAt;
    private LocalDateTime eventEndAt;
    private Long participateNum;
    private String location;
    private String category;
    private Boolean isActive;

    public static EventBasicWithoutHostInfoResponseDTO from(EventBasicEntity eventBasicEntity) {
        return EventBasicWithoutHostInfoResponseDTO.builder()
                .id(eventBasicEntity.getId())
                .hostId(eventBasicEntity.getHostId())
                .title(eventBasicEntity.getTitle())
                .eventStartAt(eventBasicEntity.getEventStartAt())
                .eventEndAt(eventBasicEntity.getEventEndAt())
                .participateNum(eventBasicEntity.getParticipateNum())
                .location(eventBasicEntity.getLocation())
                .category(Category.getNamefromId(eventBasicEntity.getCategory()))
                .isActive(eventBasicEntity.getIsActive())
                .build();
    }

}
