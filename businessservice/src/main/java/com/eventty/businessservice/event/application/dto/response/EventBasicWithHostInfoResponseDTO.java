package com.eventty.businessservice.event.application.dto.response;

import com.eventty.businessservice.event.api.dto.response.HostFindByIdResponseDTO;
import com.eventty.businessservice.event.domain.Enum.Category;
import com.eventty.businessservice.event.domain.entity.EventBasicEntity;
import lombok.*;

import java.time.LocalDateTime;

// 이벤트 상세 페이지에서 사용할 DTO (Host 정보 포함)
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class EventBasicWithHostInfoResponseDTO {
    private Long id;
    private Long hostId;
    private String title;
    private LocalDateTime eventStartAt;
    private LocalDateTime eventEndAt;
    private Long participateNum;
    private String location;
    private String category;
    private Boolean isActive;

    // Host 정보의 경우, 상세 페이지에서만 조회. 메인 화면에서는 노출 안함
    // Host 정보는 User Server API를 통해 가져옴
    private String hostName;
    private String hostPhone;

    public static EventBasicWithHostInfoResponseDTO from(EventBasicEntity eventBasicEntity, HostFindByIdResponseDTO hostInfo) {
        return EventBasicWithHostInfoResponseDTO.builder()
                .id(eventBasicEntity.getId())
                .hostId(eventBasicEntity.getHostId())
                .title(eventBasicEntity.getTitle())
                .eventStartAt(eventBasicEntity.getEventStartAt())
                .eventEndAt(eventBasicEntity.getEventEndAt())
                .participateNum(eventBasicEntity.getParticipateNum())
                .location(eventBasicEntity.getLocation())
                .category(Category.getNamefromId(eventBasicEntity.getCategory()))
                .isActive(eventBasicEntity.getIsActive())
                .hostName(hostInfo.getName())
                .hostPhone(hostInfo.getPhone())
                .build();
    }

}
