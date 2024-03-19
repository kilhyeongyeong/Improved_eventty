package com.eventty.businessservice.event.domain.repository;

import com.eventty.businessservice.event.domain.entity.EventBasicEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EventBasicRepository {
    EventBasicEntity selectEventById(Long id);
    List<EventBasicEntity> selectAllEvents();
    List<EventBasicEntity> selectEventsByHostId(Long userId);
    List<EventBasicEntity> selectEventsByIdList(List<Long> idList);
    List<EventBasicEntity> selectEventsByCategory(Long categoryId);
    List<EventBasicEntity> selectEventsBySearch(String keyword);
    Long insertEvent(EventBasicEntity request);
    Long updateEvent(EventBasicEntity request);
    Long deleteEvent(Long id);
}
