package com.eventty.businessservice.presentation;

import com.eventty.businessservice.event.application.dto.response.FullEventFindAllResponseDTO;
import com.eventty.businessservice.event.application.dto.response.FullEventFindByIdResponseDTO;
import com.eventty.businessservice.event.application.service.Facade.EventService;
import com.eventty.businessservice.event.presentation.controller.EventController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.eventty.businessservice.utils.TestUtil.*;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class) // This annotation includes @Autowired for MockMvc
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EventService eventBasicService;

    @Test
    @DisplayName("전체 행사 조회 테스트")
    public void findAllEventsTest() throws Exception {
        // Given
        List<FullEventFindAllResponseDTO> mockEventList = createFullEventFindAllResponseDTOList(3L);
        when(eventBasicService.findAllEvents()).thenReturn(mockEventList);

        // When & Then
        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.successResponseDTO.data").isArray())
                .andExpect(jsonPath("$.successResponseDTO.data.length()").value(mockEventList.size()));

        verify(eventBasicService, times(1)).findAllEvents();
    }

    @Test
    @DisplayName("행사 검색 테스트")
    public void searchEventsTest() throws Exception {
        // Given
        String keyword = "Sample";
        when(eventBasicService.findEventsBySearch(keyword)).thenReturn(createFullEventFindAllResponseDTOList(3L));

        // When & Then
        mockMvc.perform(get("/events/search")
                        .param("keyword", keyword))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.isSuccess").value(true));

        verify(eventBasicService, times(1)).findEventsBySearch(keyword);
    }

    @Test
    @DisplayName("특정 행사 조회 테스트")
    public void findEventByIdTest() throws Exception {
        // Given
        Long eventId = 1L;
        FullEventFindByIdResponseDTO MockEvent = createFullEventFindByIdResponseDTO(eventId);
        when(eventBasicService.findEventById(eventId)).thenReturn(MockEvent);

        // When & Then
        mockMvc.perform(get("/events/{eventId}", eventId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.successResponseDTO.data.id", equalTo(eventId.intValue())))
                .andExpect(jsonPath("$.successResponseDTO.data.title", equalTo("Test Event")));

        verify(eventBasicService, times(1)).findEventById(eventId);
    }

    @Test
    @DisplayName("주최자가 등록한 행사 조회 테스트")
    public void findEventsByHostIdTest() throws Exception {
        // Given
        Long hostId = 1L;
        List<FullEventFindAllResponseDTO> mockEventList = createFullEventFindAllResponseDTOList(3L);
        when(eventBasicService.findEventsByHostId(hostId)).thenReturn(mockEventList);

        // When & Then
        mockMvc.perform(get("/events/host/{hostId}", hostId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.successResponseDTO.data").isArray())
                .andExpect(jsonPath("$.successResponseDTO.data.length()").value(mockEventList.size()));

        verify(eventBasicService, times(1)).findEventsByHostId(hostId);
    }

}
