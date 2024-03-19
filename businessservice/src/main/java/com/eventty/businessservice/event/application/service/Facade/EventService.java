package com.eventty.businessservice.event.application.service.Facade;

import com.eventty.businessservice.event.application.dto.request.EventCreateRequestDTO;
import com.eventty.businessservice.event.application.dto.request.EventUpdateRequestDTO;
import com.eventty.businessservice.event.application.dto.request.TicketUpdateRequestDTO;
import com.eventty.businessservice.event.application.dto.response.*;
import com.eventty.businessservice.event.application.service.subservices.EventBasicService;
import com.eventty.businessservice.event.application.service.subservices.EventDetailService;
import com.eventty.businessservice.event.application.service.subservices.ImageService;
import com.eventty.businessservice.event.application.service.subservices.TicketService;
import com.eventty.businessservice.event.domain.Enum.Category;
import com.eventty.businessservice.event.domain.entity.TicketEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EventService {

    private final EventBasicService eventBasicService;
    private final EventDetailService eventDetailService;
    private final TicketService ticketService;
    private final ImageService imageService;

    @Transactional(readOnly = true)
    public List<FullEventFindAllResponseDTO> findAllEvents() {
        // 이벤트 기본 정보
        List<EventBasicWithoutHostInfoResponseDTO> eventBasicList = eventBasicService.findAllEvents();

        // 각 이벤트의 이미지와 함께 응답
        return mapEventBasicListToFullResponseDTO(eventBasicList);
    }

    @Transactional(readOnly = true)
    public Map<String, List<FullEventFindAllResponseDTO>> findTop10Events() {
        Map<String, List<FullEventFindAllResponseDTO>> result = new HashMap<>();

        List<String> criteriaList = Arrays.asList("Views", "CreatedAt", "ApplyEndAt");

        criteriaList.forEach(criteria -> {
            String key = "Top10" + criteria;
            List<Long> eventIds = getEventIdsByCriteria(criteria);
            List<FullEventFindAllResponseDTO> eventFullList = mapEventIdListToEventFullFindAllResponseDTO(eventIds);
            result.put(key, eventFullList);
        });

        return result;
    }

    @Transactional(readOnly = true)
    public List<FullEventFindAllResponseDTO> findEventsByHostId(Long hostId) {
        // 이벤트 기본 정보
        List<EventBasicWithoutHostInfoResponseDTO> eventBasicList = eventBasicService.findEventsByHostId(hostId);

        // 각 이벤트의 이미지와 함께 응답
        return mapEventBasicListToFullResponseDTO(eventBasicList);
    }

    @Transactional(readOnly = true)
    public List<FullEventFindAllResponseDTO> findEventsByCategory(Category category) {
        // 이벤트 기본 정보
        List<EventBasicWithoutHostInfoResponseDTO> eventBasicList = eventBasicService.findEventsByCategory(category);

        // 각 이벤트의 이미지와 함께 응답
        return mapEventBasicListToFullResponseDTO(eventBasicList);
    }

    @Transactional(readOnly = true)
    public List<FullEventFindAllResponseDTO> findEventsBySearch(String keyword) {
        // 이벤트 기본 정보
        List<EventBasicWithoutHostInfoResponseDTO> eventBasicList = eventBasicService.findEventsBySearch(keyword);

        // 각 이벤트의 이미지와 함께 응답
        return mapEventBasicListToFullResponseDTO(eventBasicList);
    }

    public FullEventFindByIdResponseDTO findEventById(Long eventId) {
        // 이벤트 기본 정보
        EventBasicWithHostInfoResponseDTO eventBasic = eventBasicService.findEventByIdWithHostInfo(eventId);

        // 이벤트 상세 정보
        EventDetailFindByIdResponseDTO eventDetail = eventDetailService.findEventById(eventId);
        eventDetailService.increaseView(eventId); // 조회수 증가 (비동기)

        // 티켓 정보
        List<TicketResponseDTO> tickets = ticketService.findTicketsByEventId(eventId);

        // 이벤트 이미지
        ImageResponseDTO imageInfo = imageService.findImageByEventId(eventId);

        return FullEventFindByIdResponseDTO.from(eventBasic, eventDetail, tickets, imageInfo);
    }

    public Long createEvent(Long hostId, EventCreateRequestDTO eventCreateRequestDTO, MultipartFile image) {
        // 이벤트의 호스트 등록
        eventCreateRequestDTO.setUserId(hostId);

        // 이벤트 기본 정보
        Long eventId = eventBasicService.createEvent(eventCreateRequestDTO);

        // 이벤트 상세 정보
        eventDetailService.createEventDetail(eventId, eventCreateRequestDTO);

        // 티켓 정보
        ticketService.createTickets(eventId, eventCreateRequestDTO);

        // 이벤트 이미지
        if(image != null){
            imageService.uploadEventImage(eventId, image);
        }

        return eventId;
    }

    public Long updateEvent(Long hostId, Long eventId, EventUpdateRequestDTO eventUpdateRequestDTO){
        // 호스트가 주최한 이벤트인지 확인
        eventBasicService.checkHostId(hostId, eventId);

        // 티켓 정보 : title, price, quantity, description 수정 가능
        Long newParticipateNum = ticketService.updateTickets(eventId, eventUpdateRequestDTO.getTicketList());
        eventUpdateRequestDTO.setParticipateNum(newParticipateNum); // 업데이트된 티켓 수량을 기준으로 참가 인원수 수정

        // 이벤트 기본 정보 : title, eventStartAt, eventEndAt, location, category, isActive 수정 가능
        eventBasicService.updateEvent(eventId, eventUpdateRequestDTO);

        // 이벤트 상세 정보 : content, applyStartAt, applyEndAt 수정 가능
        eventDetailService.updateEventDetail(eventId, eventUpdateRequestDTO);

        return eventId;
    }

    public Long deleteEvent(Long hostId, Long eventId){
        // 호스트가 주최한 이벤트인지 확인
        eventBasicService.checkHostId(hostId, eventId);

        // 이벤트 기본 정보
        eventBasicService.deleteEvent(eventId);

        // 이벤트 상세 정보
        eventDetailService.deleteEventDetail(eventId);

        // 티켓 정보
        ticketService.deleteTickets(eventId);

        // 이벤트 이미지
        imageService.deleteEventImage(eventId);

        return eventId;
    }

    public Long updateTicket(Long hostId, Long ticketId, TicketUpdateRequestDTO ticketUpdateRequestDTO) {
        // 호스트가 주최한 이벤트인지 확인
        checkTicketHostId(hostId, ticketId);

        // 티켓 정보
        return ticketService.updateTicket(ticketId, ticketUpdateRequestDTO);
    }

    public Long deleteTicket(Long hostId, Long ticketId) {
        // 호스트가 주최한 이벤트의 티켓인지 확인
        checkTicketHostId(hostId, ticketId);

        // 티켓 정보
        TicketEntity ticket = ticketService.deleteTicket(ticketId);

        // 티켓 삭제된 만큼 이벤트 인원 수 감소
        eventBasicService.subtractParticipateNum(ticket.getEventId(), ticket.getQuantity());

        return ticketId;
    }

    public List<EventInfoApiResponseDTO> findByTicketIds(List<Long> ticketIds) {

        List<TicketResponseDTO> ticketList = ticketService.findTicketsByTicketIdList(ticketIds);

        return ticketList.stream()
                .map(ticket -> {
                    EventBasicWithoutHostInfoResponseDTO eventBasic = eventBasicService.findEventByIdWithoutHostInfo(ticket.getEventId());
                    ImageResponseDTO imageInfo = imageService.findImageByEventId(ticket.getEventId());
                    return EventInfoApiResponseDTO.from(imageInfo, eventBasic, ticket);
                })
                .toList();
    }

    private List<Long> getEventIdsByCriteria(String criteria) {
        return switch (criteria) {
            case "Views" -> eventDetailService.findTop10EventsIdByViews();
            case "CreatedAt" -> eventDetailService.findTop10EventsIdByCreatedAt();
            case "ApplyEndAt" -> eventDetailService.findTop10EventsIdByApplyEndAt();
            default -> throw new IllegalArgumentException("Invalid criteria: " + criteria);
        };
    }

    private List<FullEventFindAllResponseDTO> mapEventIdListToEventFullFindAllResponseDTO(List<Long> eventIds) {
        List<EventBasicWithoutHostInfoResponseDTO> eventBasicList = eventBasicService.findEventsByIdList(eventIds);
        return eventBasicList.stream()
                .map(eventBasic -> {
                    ImageResponseDTO imageInfo = imageService.findImageByEventId(eventBasic.getId());
                    return FullEventFindAllResponseDTO.from(eventBasic, imageInfo);
                })
                .toList();
    }

    private List<FullEventFindAllResponseDTO> mapEventBasicListToFullResponseDTO(List<EventBasicWithoutHostInfoResponseDTO> eventBasicList) {
        return eventBasicList.stream()
                .map(eventBasic -> {
                    ImageResponseDTO imageInfo = imageService.findImageByEventId(eventBasic.getId());
                    return FullEventFindAllResponseDTO.from(eventBasic, imageInfo);
                })
                .collect(Collectors.toList());
    }

    public void checkTicketHostId(Long hostId, Long ticketId) {
        Long eventIdOfTicket = ticketService.findEventIdByTicketId(ticketId);
        eventBasicService.checkHostId(hostId, eventIdOfTicket);
    }

}
