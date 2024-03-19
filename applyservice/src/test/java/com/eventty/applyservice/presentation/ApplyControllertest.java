//package com.eventty.applyservice.presentation;
//
//import com.eventty.applyservice.application.ApplyServiceImpl;
//import com.eventty.applyservice.application.dto.request.CreateApplyRequestDTO;
//import com.eventty.applyservice.infrastructure.AuthenticationInterceptor;
//import com.eventty.applyservice.infrastructure.BasicSecurityConfig;
//import com.eventty.applyservice.infrastructure.WebConfig;
//import com.eventty.applyservice.presentation.exception.DataErrorLogger;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.mock.mockito.MockBeans;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(ApplyController.class)
//@MockBeans(value = {
//        @MockBean(ApplyServiceImpl.class),
//        @MockBean(WebConfig.class),
//        @MockBean(BasicSecurityConfig.class),
//        @MockBean(DataErrorLogger.class),
//        @MockBean(AuthenticationInterceptor.class)
//})
//public class ApplyControllertest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private WebApplicationContext context;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    public void setMockMvc(){
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
//    }
//
//    @Test
//    @DisplayName("[POST][Success] 행사 신청")
//    @WithMockCustom(authorities = {"USER"})
//    public void createApplySuccessTest() throws Exception{
//        // Assignment
//        Long eventId = 1L;
//        Long ticketId = 1L;
//        Long quantity = 22L;
//        Long applicantNum = 3L;
//        String phone = "010-1234-1234";
//
//        String url = "/applies";
//
//        CreateApplyRequestDTO createApplyRequestDTO = CreateApplyRequestDTO
//                .builder()
//                .eventId(eventId)
//                .ticketId(ticketId)
//                .quantity(quantity)
//                .applicantNum(applicantNum)
//                .phone(phone)
//                .build();
//        final String requestBody = objectMapper.writeValueAsString(createApplyRequestDTO);
//
//        // Act
//        final ResultActions response = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestBody)
//        );
//
//        // Assert
//        response
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("success").value(true))
//                .andExpect(jsonPath("successResponseDTO").doesNotHaveJsonPath())
//                .andExpect(jsonPath("errorResponseDTO").doesNotHaveJsonPath());
//    }
//
//    @Test
//    @DisplayName("[POST][Fail - ticketId null value] 이벤트 신청")
//    @WithMockCustom(authorities = {"USER"})
//    public void createApplyTicketIdNullValueFailTest() throws Exception{
//        // Assignment
//        Long eventId = 3L;
//        Long ticketId = null;
//        Long quantity = 22L;
//        Long applicantNum = 3L;
//        String phone = "010-7411-7411";
//        String url = "/applies";
//
//        CreateApplyRequestDTO createApplyRequestDTO = CreateApplyRequestDTO
//                .builder()
//                .ticketId(ticketId)
//                .eventId(eventId)
//                .quantity(quantity)
//                .phone(phone)
//                .applicantNum(applicantNum)
//                .build();
//        final String requestBody = objectMapper.writeValueAsString(createApplyRequestDTO);
//
//        final ResultActions response = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestBody));
//
//        response
//                .andExpect(status().is4xxClientError())
//                .andExpect(jsonPath("success").value(false))
//                .andExpect(jsonPath("successResponseDTO").doesNotHaveJsonPath())
//                .andExpect(jsonPath("errorResponseDTO").hasJsonPath());
//
//    }
//
//    @Test
//    @DisplayName("[DELETE][Success] 행사 신청 취소")
//    @WithMockCustom(authorities = {"USER"})
//    public void cancelApplySuccessTest() throws Exception{
//        // Assignment
//        Long applyId = 1L;
//        String url = "/applies/" + applyId;
//
//        // Act
//        final ResultActions response = mockMvc.perform(delete(url).contentType(MediaType.APPLICATION_JSON));
//
//        // Assert
//        response
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("success").value(true))
//                .andExpect(jsonPath("successResponseDTO").doesNotHaveJsonPath())
//                .andExpect(jsonPath("errorResponseDTO").doesNotHaveJsonPath());
//    }
//
//    @Test
//    @DisplayName("[DELETE][FAIL - applyId is null value]")
//    @WithMockCustom(authorities = {"USER"})
//    public void cancelApplyApplyIdNullValueFailTest() throws Exception{
//        // Assignment
//        Long applyId = null;
//        String url = "/applies/" + applyId;
//
//        // Act
//        final ResultActions response = mockMvc.perform(delete(url).contentType(MediaType.APPLICATION_JSON));
//
//        // Assert
//        response
//                .andExpect(status().is4xxClientError())
//                .andExpect(jsonPath("success").value(false))
//                .andExpect(jsonPath("successResponseDTO").doesNotHaveJsonPath())
//                .andExpect(jsonPath("errorResponseDTO").hasJsonPath());
//    }
//}
