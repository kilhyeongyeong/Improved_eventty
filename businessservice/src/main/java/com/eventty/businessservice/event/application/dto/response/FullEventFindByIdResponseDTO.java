package com.eventty.businessservice.event.application.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class FullEventFindByIdResponseDTO {
    // eventBasic
    private Long id;
    private Long hostId;
    private String hostName; // from User Server
    private String hostPhone; // from User Server
    private String title;
    private LocalDateTime eventStartAt;
    private LocalDateTime eventEndAt;
    private Long participateNum;
    private String location;
    private String category;
    private Boolean isActive;

    // eventDetail
    private String content;
    private LocalDateTime applyStartAt;
    private LocalDateTime applyEndAt;
    private Long views;

    // tickets
    private List<TicketResponseDTO> tickets;

    // imageInfo
    private String image; // 이미지 파일
    private String originFileName; // 원본 파일명


    public static FullEventFindByIdResponseDTO from(
            EventBasicWithHostInfoResponseDTO eventBasic,
            EventDetailFindByIdResponseDTO eventDetail,
            List<TicketResponseDTO> tickets,
            ImageResponseDTO imageInfo) {
        return FullEventFindByIdResponseDTO.builder()
                .id(eventBasic.getId())
                .hostId(eventBasic.getHostId())
                .hostName(eventBasic.getHostName())
                .hostPhone(eventBasic.getHostPhone())
                .title(eventBasic.getTitle())
                .eventStartAt(eventBasic.getEventStartAt())
                .eventEndAt(eventBasic.getEventEndAt())
                .participateNum(eventBasic.getParticipateNum())
                .location(eventBasic.getLocation())
                .category(eventBasic.getCategory())
                .isActive(eventBasic.getIsActive())
                .content(eventDetail.getContent())
                .applyStartAt(eventDetail.getApplyStartAt())
                .applyEndAt(eventDetail.getApplyEndAt())
                .views(eventDetail.getViews())
                .tickets(tickets)
                .image(imageInfo.getImagePathFromStorage())
                .originFileName(imageInfo.getImageOriginalFileName())
            .build();
    }


    // Swagger 을 위하여 기본 생성자로 기본값 설정
    public FullEventFindByIdResponseDTO() {
this.id = 1L;
        this.hostId = 1L;
        this.hostName = "Test Host 1";
        this.hostPhone = "010-1234-5678";
        this.title = "Test Event 1";
        this.eventStartAt = LocalDateTime.now();
        this.eventEndAt = LocalDateTime.now();
        this.participateNum = 50L;
        this.location = "Location 1";
        this.category = "sports";
        this.isActive = true;
        this.content = "Test Content 1";
        this.applyStartAt = LocalDateTime.now();
        this.applyEndAt = LocalDateTime.now();
        this.views = 100L;
        this.tickets = new ArrayList<>();
        this.image = "https://eventty-storage.s3.ap-northeast-2.amazonaws.com/2021/08/15/1629025200_1.jpg";
        this.originFileName = "1629025200_1.jpg";
    }
}
