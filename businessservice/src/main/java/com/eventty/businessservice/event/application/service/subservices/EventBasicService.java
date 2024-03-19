package com.eventty.businessservice.event.application.service.subservices;

import com.eventty.businessservice.event.api.ApiClient;
import com.eventty.businessservice.event.api.dto.response.HostFindByIdResponseDTO;
import com.eventty.businessservice.event.application.dto.request.EventCreateRequestDTO;
import com.eventty.businessservice.event.application.dto.request.EventUpdateRequestDTO;
import com.eventty.businessservice.event.application.dto.request.TicketCreateRequestDTO;
import com.eventty.businessservice.event.application.dto.response.EventBasicWithoutHostInfoResponseDTO;
import com.eventty.businessservice.event.application.dto.response.EventBasicWithHostInfoResponseDTO;
import com.eventty.businessservice.event.domain.exception.HostInfoNotFoundException;
import com.eventty.businessservice.event.domain.Enum.Category;
import com.eventty.businessservice.event.domain.entity.EventBasicEntity;
import com.eventty.businessservice.event.domain.exception.EventNotFoundException;
import com.eventty.businessservice.event.domain.exception.AccessDeniedException;
import com.eventty.businessservice.event.domain.repository.EventBasicRepository;
import com.eventty.businessservice.event.presentation.response.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventBasicService {

    private final EventBasicRepository eventBasicRepository;
    private final ApiClient apiClient;

    public List<EventBasicWithoutHostInfoResponseDTO> findAllEvents(){
        return Optional.ofNullable(eventBasicRepository.selectAllEvents())
                .map(events -> events.stream()
                .map(EventBasicWithoutHostInfoResponseDTO::from)
                .collect(Collectors.toList()))
                .orElseThrow(()->EventNotFoundException.EXCEPTION);
    }

    public List<EventBasicWithoutHostInfoResponseDTO> findEventsByIdList(List<Long> eventIdList){
        return Optional.ofNullable(eventBasicRepository.selectEventsByIdList(eventIdList))
                .filter(events -> !events.isEmpty())
                .orElseThrow(() -> EventNotFoundException.EXCEPTION)
                .stream()
                .map(EventBasicWithoutHostInfoResponseDTO::from)
                .collect(Collectors.toList());
    }

    public List<EventBasicWithoutHostInfoResponseDTO> findEventsByHostId(Long hostId){
        return Optional.ofNullable(eventBasicRepository.selectEventsByHostId(hostId))
                .filter(events -> !events.isEmpty())
                .orElseThrow(() -> EventNotFoundException.EXCEPTION)
                .stream()
                .map(EventBasicWithoutHostInfoResponseDTO::from)
                .collect(Collectors.toList());
    }

    public List<EventBasicWithoutHostInfoResponseDTO> findEventsByCategory(Category category){
        return Optional.ofNullable(eventBasicRepository.selectEventsByCategory(category.getId()))
                .filter(events -> !events.isEmpty())
                .orElseThrow(() -> EventNotFoundException.EXCEPTION)
                .stream()
                .map(EventBasicWithoutHostInfoResponseDTO::from)
                .collect(Collectors.toList());
    }

    public List<EventBasicWithoutHostInfoResponseDTO> findEventsBySearch(String keyword){
        return Optional.ofNullable(eventBasicRepository.selectEventsBySearch(keyword))
                .filter(events -> !events.isEmpty())
                .orElseThrow(() -> EventNotFoundException.EXCEPTION)
                .stream()
                .map(EventBasicWithoutHostInfoResponseDTO::from)
                .collect(Collectors.toList());
    }

    public EventBasicWithHostInfoResponseDTO findEventByIdWithHostInfo(Long eventId) {
        EventBasicEntity eventBasic = getEventIfExists(eventId);

        // 이벤트 상세 조회 시 호스트 정보도 함께 응답하기 위하여
        // User Server API 호출하여 Host 정보 가져오기
        HostFindByIdResponseDTO hostInfo = getUserInfoForEventHost(eventBasic.getHostId());

        return EventBasicWithHostInfoResponseDTO.from(eventBasic, hostInfo);
    }

    public EventBasicWithoutHostInfoResponseDTO findEventByIdWithoutHostInfo(Long eventId) {
        EventBasicEntity eventBasic = getEventIfExists(eventId);

        return EventBasicWithoutHostInfoResponseDTO.from(eventBasic);
    }


    public Long createEvent(EventCreateRequestDTO eventCreateRequestDTO) {
        // 참가 인원수 계산
        Long participateNum = calculateParticipateNum(eventCreateRequestDTO.getTickets());
        eventCreateRequestDTO.setParticipateNum(participateNum);

        EventBasicEntity eventBasic = eventCreateRequestDTO.toEventBasicEntity();
        eventBasicRepository.insertEvent(eventBasic);

        return eventBasic.getId();
    }

    public Long updateEvent(Long eventId, EventUpdateRequestDTO eventUpdateRequestDTO){
        // 업데이트 전, 해당 데이터 존재 여부 확인
        EventBasicEntity eventBasic = getEventIfExists(eventId);

        eventBasic.updateEventBasic(eventUpdateRequestDTO);
        eventBasicRepository.updateEvent(eventBasic);

        return eventId;
    }

    public Long deleteEvent(Long eventId){
        // 삭제 전, 해당 데이터 존재 여부 확인
        EventBasicEntity eventBasic = getEventIfExists(eventId);

        eventBasicRepository.deleteEvent(eventId);

        return eventId;
    }

    public void subtractParticipateNum(Long eventId, Long quantity) {
        // 인원수 감소 전, 해당 데이터 존재 여부 확인
        EventBasicEntity eventBasic = getEventIfExists(eventId);

        eventBasic.subtractParticipateNum(quantity);
        eventBasicRepository.updateEvent(eventBasic);
    }

    public void checkHostId(Long hostId, Long eventId) {
        EventBasicEntity eventBasic = getEventIfExists(eventId);

        if(!eventBasic.getHostId().equals(hostId)){
            throw AccessDeniedException.EXCEPTION;
        }
    }

    private EventBasicEntity getEventIfExists(Long eventId) {
        Optional<EventBasicEntity> eventOptional = Optional.ofNullable(eventBasicRepository.selectEventById(eventId));
        return eventOptional.orElseThrow(() -> EventNotFoundException.EXCEPTION);
    }

    private Long calculateParticipateNum(List<TicketCreateRequestDTO> tickets) {
        return tickets.stream()
                .mapToLong(TicketCreateRequestDTO::getQuantity)
                .sum();
    }

    // User Info API 호출하여 Host 정보 가져오기
    private HostFindByIdResponseDTO getUserInfoForEventHost(Long hostId) {
        ResponseEntity<ResponseDTO<HostFindByIdResponseDTO>> hostInfoResponse = apiClient.queryUserInfoApi(hostId);
        ResponseDTO<HostFindByIdResponseDTO> responseBody = hostInfoResponse.getBody();

        if (responseBody == null || !responseBody.getIsSuccess()) {
            throw HostInfoNotFoundException.EXCEPTION;
        }

        return responseBody.getSuccessResponseDTO().getData();
    }
}
