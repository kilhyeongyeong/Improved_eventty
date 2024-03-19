package com.eventty.businessservice.application;

import com.eventty.businessservice.event.application.dto.response.EventBasicWithoutHostInfoResponseDTO;
import com.eventty.businessservice.event.application.service.subservices.EventBasicService;
import com.eventty.businessservice.event.domain.Enum.Category;
import com.eventty.businessservice.event.domain.entity.EventBasicEntity;
import com.eventty.businessservice.event.domain.exception.EventNotFoundException;
import com.eventty.businessservice.event.domain.repository.EventBasicRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.eventty.businessservice.utils.TestUtil.createFakeEventEntityList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventBasicServiceTest {

    @Mock
    private EventBasicRepository eventBasicRepository;

    @InjectMocks
    private EventBasicService eventBasicService;

    @Test
    @DisplayName("전체 이벤트 조회 테스트")
    public void findAllEventsTest() {
        // Given
        List<EventBasicEntity> mockEventEntities = createFakeEventEntityList(3L);
        when(eventBasicRepository.selectAllEvents()).thenReturn(mockEventEntities);

        // When
        List<EventBasicWithoutHostInfoResponseDTO> responseDTOs = eventBasicService.findAllEvents();

        // Then
        assertEquals(mockEventEntities.size(), responseDTOs.size());
        verify(eventBasicRepository, times(1)).selectAllEvents();
    }

    @Test
    @DisplayName("주최자가 등록한 이벤트 조회 테스트")
    public void findEventsByHostIdTest() {
        // given
        Long hostId = 1L;
        List<EventBasicEntity> mockEvents = createFakeEventEntityList(3L);
        when(eventBasicRepository.selectEventsByHostId(hostId)).thenReturn(mockEvents);

        // when
        List<EventBasicWithoutHostInfoResponseDTO> result = eventBasicService.findEventsByHostId(hostId);

        // then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(mockEvents.size(), result.size());
    }

    @Test
    @DisplayName("이벤트 카테고리별 조회 테스트")
    public void findEventsByCategoryTest() {
        // given
        Category category = Category.sports;
        List<EventBasicEntity> mockEvents = createFakeEventEntityList(3L);
        when(eventBasicRepository.selectEventsByCategory(category.getId())).thenReturn(mockEvents);

        // when
        List<EventBasicWithoutHostInfoResponseDTO> result = eventBasicService.findEventsByCategory(category);

        // then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(mockEvents.size(), result.size());
    }

    @Test
    @DisplayName("이벤트 카테고리별 조회 테스트 - 이벤트 없음")
    public void findEventsByCategoryTest_NoEventsFound() {
        // given
        Category category = Category.it;
        when(eventBasicRepository.selectEventsByCategory(category.getId())).thenReturn(new ArrayList<>());

        // when & then
        assertThrows(EventNotFoundException.class, () -> {
            eventBasicService.findEventsByCategory(category);
        });
    }

    @Test
    @DisplayName("이벤트 검색 테스트")
    public void findEventsBySearchTest() {
        // given
        String keyword = "Sample";
        List<EventBasicEntity> mockEvents = createFakeEventEntityList(3L);
        when(eventBasicRepository.selectEventsBySearch(keyword)).thenReturn(mockEvents);

        // when
        List<EventBasicWithoutHostInfoResponseDTO> result = eventBasicService.findEventsBySearch(keyword);

        // then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(mockEvents.size(), result.size());
    }

}
