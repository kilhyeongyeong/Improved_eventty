package com.eventty.businessservice.application;

import com.eventty.businessservice.event.application.dto.request.EventCreateRequestDTO;
import com.eventty.businessservice.event.application.dto.request.EventUpdateRequestDTO;
import com.eventty.businessservice.event.application.dto.request.TicketUpdateRequestDTO;
import com.eventty.businessservice.event.application.dto.response.*;
import com.eventty.businessservice.event.application.service.Facade.EventService;
import com.eventty.businessservice.event.application.service.subservices.EventBasicService;
import com.eventty.businessservice.event.application.service.subservices.EventDetailService;
import com.eventty.businessservice.event.application.service.subservices.ImageService;
import com.eventty.businessservice.event.application.service.subservices.TicketService;
import com.eventty.businessservice.event.domain.entity.TicketEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Arrays;
import java.util.List;

import static com.eventty.businessservice.utils.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EventServiceTest {

    @InjectMocks
    private EventService eventService;

    @Mock
    private EventBasicService eventBasicService;

    @Mock
    private EventDetailService eventDetailService;

    @Mock
    private TicketService ticketService;

    @Mock
    private ImageService imageService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllEvents() {
        // given
        EventBasicWithoutHostInfoResponseDTO fakeEvent1 = createFakeEventBasicDTO(1L);
        EventBasicWithoutHostInfoResponseDTO fakeEvent2 = createFakeEventBasicDTO(2L);

        // eventBasicService의 findAllEvents 메서드 호출 시 가짜 데이터 반환하도록 설정
        when(eventBasicService.findAllEvents()).thenReturn(Arrays.asList(fakeEvent1, fakeEvent2));
        // imageService의 findImageByEventId 메서드 호출 시 가짜 이미지 데이터 반환하도록 설정
        when(imageService.findImageByEventId(1L)).thenReturn(createFakeImageResponseDTO());
        when(imageService.findImageByEventId(2L)).thenReturn(createFakeImageResponseDTO());

        // when
        List<FullEventFindAllResponseDTO> result = eventService.findAllEvents();

        // then
        assertEquals(2, result.size());
        verify(eventBasicService, times(1)).findAllEvents();
        verify(imageService, times(2)).findImageByEventId(anyLong());
    }


    @Test
    public void testFindEventById() {
        // given
        EventBasicWithHostInfoResponseDTO fakeEventBasic = createFakeEventBasicWithHostInfoDTO(1L);
        EventDetailFindByIdResponseDTO fakeEventDetail = createFakeEventDetailDTO();
        List<TicketResponseDTO> fakeTickets = createFakeTicketResponseDTOList();
        ImageResponseDTO fakeImageInfo = createFakeImageResponseDTO();

        when(eventBasicService.findEventByIdWithHostInfo(anyLong())).thenReturn(fakeEventBasic);
        when(eventDetailService.findEventById(anyLong())).thenReturn(fakeEventDetail);
        when(ticketService.findTicketsByEventId(anyLong())).thenReturn(fakeTickets);
        when(imageService.findImageByEventId(anyLong())).thenReturn(fakeImageInfo);

        // when
        FullEventFindByIdResponseDTO result = eventService.findEventById(1L);

        // then
        verify(eventBasicService, times(1)).findEventByIdWithHostInfo(anyLong());
        verify(eventDetailService, times(1)).findEventById(anyLong());
        verify(ticketService, times(1)).findTicketsByEventId(anyLong());
        verify(imageService, times(1)).findImageByEventId(anyLong());

        assertEquals(fakeEventBasic.getId(), result.getId());
        assertEquals(fakeEventDetail.getApplyEndAt(), result.getApplyEndAt());
    }

    @Test
    public void testCreateEvent() {
        // given
        EventCreateRequestDTO fakeEventCreateRequest = createFakeEventCreateRequestDTO();
        MockMultipartFile fakeImage = createFakeImageMultipartFile();

        // eventBasicService의 createEvent 메서드 호출 시 가짜 이벤트 ID 반환하도록 설정
        when(eventBasicService.createEvent(any())).thenReturn(1L);

        // when
        Long eventId = eventService.createEvent(123L, fakeEventCreateRequest, fakeImage);

        // then
        verify(eventBasicService, times(1)).createEvent(any());
        verify(eventDetailService, times(1)).createEventDetail(1L, fakeEventCreateRequest);
        verify(ticketService, times(1)).createTickets(1L, fakeEventCreateRequest);
        verify(imageService, times(1)).uploadEventImage(1L, fakeImage);

        assertEquals(1L, eventId);
    }

    @Test
    public void testUpdateEvent() {
        // given
        Long hostId = 123L;
        Long eventId = 1L;
        EventUpdateRequestDTO fakeEventUpdateRequest = createFakeEventUpdateRequestDTO();

        doNothing().when(eventBasicService).checkHostId(hostId, eventId);

        // when
        Long updatedEventId = eventService.updateEvent(hostId, eventId, fakeEventUpdateRequest);

        // then
        verify(eventBasicService, times(1)).checkHostId(hostId, eventId);
        verify(eventBasicService, times(1)).updateEvent(eventId, fakeEventUpdateRequest);
        verify(eventDetailService, times(1)).updateEventDetail(eventId, fakeEventUpdateRequest);

        assertEquals(eventId, updatedEventId);
    }

    @Test
    public void testDeleteEvent() {
        // given
        Long hostId = 123L;
        Long eventId = 1L;

        doNothing().when(eventBasicService).checkHostId(hostId, eventId);

        // when
        Long deletedEventId = eventService.deleteEvent(hostId, eventId);

        // then
        verify(eventBasicService, times(1)).checkHostId(hostId, eventId);
        verify(eventBasicService, times(1)).deleteEvent(eventId);
        verify(eventDetailService, times(1)).deleteEventDetail(eventId);
        verify(ticketService, times(1)).deleteTickets(eventId);
        verify(imageService, times(1)).deleteEventImage(eventId);

        assertEquals(eventId, deletedEventId);
    }

    @Test
    public void testUpdateTicket() {
        // given
        Long hostId = 123L;
        Long ticketId = 1L;
        Long eventIdOfTicket = 10L;
        TicketUpdateRequestDTO fakeTicketUpdateRequest = createFakeTicketUpdateRequestDTO();

        when(ticketService.findEventIdByTicketId(ticketId)).thenReturn(eventIdOfTicket);
        doNothing().when(eventBasicService).checkHostId(hostId, eventIdOfTicket);

        when(ticketService.updateTicket(ticketId, fakeTicketUpdateRequest)).thenReturn(ticketId);

        // when
        Long updatedTicketId = eventService.updateTicket(hostId, ticketId, fakeTicketUpdateRequest);

        // then
        verify(ticketService, times(1)).updateTicket(ticketId, fakeTicketUpdateRequest);

        assertEquals(ticketId, updatedTicketId);
    }

    @Test
    public void testDeleteTicket() {
        // Given
        Long hostId = 123L;
        Long ticketId = 1L;
        Long eventId = 1L; // 이벤트 ID
        Long ticketQuantity = 5L; // 티켓 수량

        when(ticketService.findEventIdByTicketId(ticketId)).thenReturn(eventId);
        doNothing().when(eventBasicService).checkHostId(hostId, eventId);

        TicketEntity fakeDeletedTicket = createFakeTicketEntity(eventId);
        when(ticketService.deleteTicket(ticketId)).thenReturn(fakeDeletedTicket);

        // When
        Long deletedTicketId = eventService.deleteTicket(hostId, ticketId);

        // Then
        verify(ticketService, times(1)).deleteTicket(ticketId);

        assertEquals(ticketId, deletedTicketId);
    }

}
