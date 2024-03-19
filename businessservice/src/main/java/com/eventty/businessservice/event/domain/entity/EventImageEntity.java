package com.eventty.businessservice.event.domain.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EventImageEntity {
    private Long id;
    private Long eventId;
    private String originalFileName;
    private String storedFilePath;
    private Long fileSize;
    private Boolean isDeleted;
}