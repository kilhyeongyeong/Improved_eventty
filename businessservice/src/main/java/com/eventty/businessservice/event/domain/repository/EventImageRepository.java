package com.eventty.businessservice.event.domain.repository;

import com.eventty.businessservice.event.domain.entity.EventImageEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EventImageRepository {
    EventImageEntity selectEventImageByEventId(Long eventId);
    Long insertEventImage(EventImageEntity eventImageEntity);
    Long deleteEventImageByEventId(Long eventId);
}
