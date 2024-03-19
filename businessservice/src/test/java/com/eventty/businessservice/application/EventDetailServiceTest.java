package com.eventty.businessservice.application;

import com.eventty.businessservice.event.application.dto.response.EventDetailFindByIdResponseDTO;
import com.eventty.businessservice.event.application.service.subservices.EventDetailService;
import com.eventty.businessservice.event.domain.entity.EventDetailEntity;
import com.eventty.businessservice.event.domain.repository.EventDetailRepository;
import com.eventty.businessservice.event.domain.exception.EventNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static com.eventty.businessservice.utils.TestUtil.*;

@ExtendWith(MockitoExtension.class)
public class EventDetailServiceTest {

    @Mock
    private EventDetailRepository eventDetailRepository;

    @InjectMocks
    private EventDetailService eventDetailService;

    @Test
    @DisplayName("특정 이벤트 조회 테스트")
    public void testFindEventById() {
        // Given
        Long eventId = 1L;
        EventDetailEntity fakeEventDetail = createFakeEventDetailEntity(eventId);
        when(eventDetailRepository.selectEventDetailById(eventId)).thenReturn(fakeEventDetail);

        // When
        EventDetailFindByIdResponseDTO eventDetailDTO = eventDetailService.findEventById(eventId);

        // Then
        verify(eventDetailRepository, times(1)).selectEventDetailById(eventId);

        assertEquals(fakeEventDetail.getId(), eventDetailDTO.getId());
    }

    @Test
    @DisplayName("존재하지 않는 이벤트 조회 시 예외 테스트")
    public void findEventByIdTest_NonExistingEvent() {
        // Given
        Long eventId = 1L;
        when(eventDetailRepository.selectEventDetailById(eventId)).thenReturn(null);

        // When & Then
        assertThrows(EventNotFoundException.class, () -> eventDetailService.findEventById(eventId));
        verify(eventDetailRepository, times(1)).selectEventDetailById(eventId);
    }
}
