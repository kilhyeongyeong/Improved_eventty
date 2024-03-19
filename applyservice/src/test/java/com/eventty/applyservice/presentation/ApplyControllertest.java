package com.eventty.applyservice.presentation;

import com.eventty.applyservice.application.ApplyServiceImpl;
import com.eventty.applyservice.application.dto.request.CreateApplyRequestDTO;
import com.eventty.applyservice.infrastructure.userContext.UserContextHolder;
import com.eventty.applyservice.presentation.exception.DataErrorLogger;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApplyController.class)
@MockBeans(value = {
        @MockBean(ApplyServiceImpl.class),
        @MockBean(DataErrorLogger.class)
})
public class ApplyControllertest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setMockMvc(){
        UserContextHolder.getContext().setUserId("1");
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @DisplayName("[POST][Success] 행사 신청")
    public void createApplySuccessTest() throws Exception{
        // Assignment
        Long eventId = 1L;
        Long ticketId = 1L;
        Long quantity = 22L;
        Long applicantNum = 3L;
        String name = "홍길동";
        String phone = "010-1234-1234";

        String url = "/applies";

        CreateApplyRequestDTO createApplyRequestDTO = CreateApplyRequestDTO
                .builder()
                .eventId(eventId)
                .ticketId(ticketId)
                .quantity(quantity)
                .applicantNum(applicantNum)
                .name(name)
                .phone(phone)
                .build();
        final String requestBody = objectMapper.writeValueAsString(createApplyRequestDTO);

        // Act
        final ResultActions response = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestBody)
        );

        // Assert
        response
                .andExpect(status().isCreated())
                .andExpect(jsonPath("isSuccess").value(true));
    }

    @Test
    @DisplayName("[POST][Fail - ticketId null value] 이벤트 신청")
    public void createApplyTicketIdNullValueFailTest() throws Exception{
        // Assignment
        Long eventId = 3L;
        Long quantity = 22L;
        Long applicantNum = 3L;
        String phone = "010-7411-7411";
        String url = "/applies";

        CreateApplyRequestDTO createApplyRequestDTO = CreateApplyRequestDTO
                .builder()
                .ticketId(null)
                .eventId(eventId)
                .quantity(quantity)
                .phone(phone)
                .applicantNum(applicantNum)
                .build();
        final String requestBody = objectMapper.writeValueAsString(createApplyRequestDTO);

        final ResultActions response = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestBody));

        response
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("isSuccess").value(false))
                .andExpect(jsonPath("errorResponseDTO").hasJsonPath());

    }

    @Test
    @DisplayName("[DELETE][Success] 행사 신청 취소")
    public void cancelApplySuccessTest() throws Exception{
        // Assignment
        Long applyId = 1L;
        String url = "/applies/" + applyId;

        // Act
        final ResultActions response = mockMvc.perform(delete(url).contentType(MediaType.APPLICATION_JSON));

        // Assert
        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("isSuccess").value(true));
    }

    @Test
    @DisplayName("[DELETE][FAIL - applyId is null value]")
    public void cancelApplyApplyIdNullValueFailTest() throws Exception{
        // Assignment
        Long applyId = null;
        String url = "/applies/" + applyId;

        // Act
        final ResultActions response = mockMvc.perform(delete(url).contentType(MediaType.APPLICATION_JSON));

        // Assert
        response
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("isSuccess").value(false))
                .andExpect(jsonPath("errorResponseDTO").hasJsonPath());
    }

    @Test
    @DisplayName("[GET][Success] 행사 신청 내역 조회")
    public void getApplicationListSuccessTest() throws Exception{
        // Assignment
        String url = "/applies";

        // Act
        final ResultActions response = mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON));

        // Assert
        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("isSuccess").value(true));
    }

    @Test
    @DisplayName("[GET][Success] 티켓별 현재 신청자 수 조회")
    public void getCurrentNumberOfApplicantsByTicketSuccessTest() throws Exception{
        // Assignment
        Long eventId = 1L;
        String url = "/api/applies/count?eventId=" + eventId;

        // Act
        final ResultActions response = mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON));

        // Assert
        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("isSuccess").value(true));
    }

    @Test
    @DisplayName("[GET][Fail - eventId is null] 티켓별 현재 신청자 수 조회")
    public void getCurrentNumberOfApplicantsByTicketEventIdNullValueFailTest() throws Exception{
        // Assignment
        String url = "/api/applies/count?eventId";

        final ResultActions response = mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON));

        // Assert
        response
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("isSuccess").value(false))
                .andExpect(jsonPath("errorResponseDTO").hasJsonPath());
    }

    @Test
    @DisplayName("[GET][Success] 주최자 - 행사 별 참여자 목록 조회")
    public void getApplicantsListByEventSuccessTest() throws Exception{
        // Assignment
        Long eventId = 1L;
        String url = "/applies/host?eventId=" + eventId;

        // Act
        final ResultActions response = mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON));

        // Assert
        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("isSuccess").value(true));
    }
}
