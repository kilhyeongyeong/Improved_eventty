package com.eventty.businessservice.event.application.service.subservices;

import com.eventty.businessservice.event.application.dto.request.EventCreateRequestDTO;
import com.eventty.businessservice.event.application.dto.request.EventUpdateRequestDTO;
import com.eventty.businessservice.event.application.dto.response.EventDetailFindByIdResponseDTO;
import com.eventty.businessservice.event.domain.entity.EventDetailEntity;
import com.eventty.businessservice.event.domain.exception.EventNotFoundException;
import com.eventty.businessservice.event.domain.repository.EventDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventDetailService {

    private final EventDetailRepository eventDetailRepository;

    public EventDetailFindByIdResponseDTO findEventById(Long eventId) {
        return Optional.ofNullable(eventDetailRepository.selectEventDetailById(eventId))
                .map(EventDetailFindByIdResponseDTO::from)
                .orElseThrow(() -> EventNotFoundException.EXCEPTION);
    }

    // 이벤트 상세 조회 후 조회수 증가 (비동기)
    @Async("asyncExecutor")
    @Transactional
    public void increaseView(Long eventId){
        eventDetailRepository.updateView(eventId);
    }

    public List<Long> findTop10EventsIdByViews() {
        return eventDetailRepository.selectTop10EventsIdByViews();
    }

    public List<Long> findTop10EventsIdByCreatedAt() {
        return eventDetailRepository.selectTop10EventsIdByCreateDate();
    }

    public List<Long> findTop10EventsIdByApplyEndAt() {
        return eventDetailRepository.selectTop10EventsIdByApplyEndAt();
    }

    public Long createEventDetail(Long eventId, EventCreateRequestDTO eventCreateRequestDTO){
        EventDetailEntity eventDetail = eventCreateRequestDTO.toEventDetailEntity(eventId);
        eventDetailRepository.insertEventDetail(eventDetail);

        return eventId;
    }

    public Long updateEventDetail(Long eventId, EventUpdateRequestDTO eventUpdateRequestDTO){
        // 업데이트 전, 해당 데이터 존재 여부 확인
        EventDetailEntity eventDetail = getEventDetailIfExists(eventId);

        eventDetail.updateEventDetail(eventUpdateRequestDTO);
        eventDetailRepository.updateEventDetail(eventDetail);

        return eventId;
    }

    public Long deleteEventDetail(Long eventId) {
        // 삭제 전, 해당 데이터 존재 여부 확인
        EventDetailEntity eventDetail = getEventDetailIfExists(eventId);

        eventDetailRepository.deleteEventDetail(eventId);
        return eventId;
    }

    private EventDetailEntity getEventDetailIfExists(Long eventId) {
        Optional<EventDetailEntity> eventOptional = Optional.ofNullable(eventDetailRepository.selectEventDetailById(eventId));
        return eventOptional.orElseThrow(() -> EventNotFoundException.EXCEPTION);
    }
}
