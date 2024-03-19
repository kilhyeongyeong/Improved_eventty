package com.eventty.businessservice.event.domain.repository;

import com.eventty.businessservice.event.domain.entity.EventDetailEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface EventDetailRepository {
    EventDetailEntity selectEventDetailById(Long id);
    List<Long> selectTop10EventsIdByViews();
    List<Long> selectTop10EventsIdByCreateDate();
    List<Long> selectTop10EventsIdByApplyEndAt();
    Long insertEventDetail(EventDetailEntity request);
    Long updateEventDetail(EventDetailEntity request);
    Long updateView(Long id);
    Long deleteEventDetail(Long id);
}
