package com.eventty.applyservice.application;

import com.eventty.applyservice.application.dto.CreateApplyDTO;
import com.eventty.applyservice.application.dto.FindApplicantsListDTO;
import com.eventty.applyservice.application.dto.FindByUserIdDTO;
import com.eventty.applyservice.application.dto.request.CreateApplyRequestDTO;
import com.eventty.applyservice.application.dto.request.FindApplicantsListRequestDTO;
import com.eventty.applyservice.application.dto.response.FindAppicaionListResponseDTO;
import com.eventty.applyservice.application.dto.response.FindApplicantsListResposneDTO;
import com.eventty.applyservice.application.dto.response.FindEventInfoResponseDTO;
import com.eventty.applyservice.application.dto.response.FindUsingTicketResponseDTO;
import com.eventty.applyservice.domain.ApplyReposiroty;
import com.eventty.applyservice.domain.code.ServerUri;
import com.eventty.applyservice.domain.exception.AlreadyCancelApplyException;
import com.eventty.applyservice.domain.exception.ExceedApplicantsException;
import com.eventty.applyservice.domain.exception.NonExistentIdException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class ApplyServiceTest {

    @InjectMocks
    private ApplyServiceImpl applyServiceImpl;

    @Mock
    private ApplyReposiroty applyReposiroty;

    @Mock
    private RestTemplate customRestTemplate;

    @Mock
    private ApiService apiService;

    @Mock
    private ServerUri serverUri;

    @Test
    @DisplayName("[Fail - 신청 인원 초과] 행사 신청")
    public void ExceedApplicantsExceptionOfCreateApplyTest(){
        Long userId = 3L;
        Long eventId = 2L;
        Long ticketId = 2L;
        Long applicantNum = 2L;
        String phone = "010-7898-4565";
        Long quantity = 2L;
        String name = "하이";

        CreateApplyRequestDTO createApplyRequestDTO = CreateApplyRequestDTO
                .builder()
                .eventId(eventId)
                .ticketId(ticketId)
                .applicantNum(applicantNum)
                .phone(phone)
                .quantity(quantity)
                .name(name)
                .build();
        when(applyReposiroty.getCurrentNumberOfApplicants(anyLong())).thenReturn(2L);

        assertThrows(ExceedApplicantsException.class, () -> applyServiceImpl.createApply(userId, createApplyRequestDTO));
    }

    @Test
    @DisplayName("[Success] 행사 신청")
    public void createApplyTest(){
        // Assignment
        Long userId = 1000L;
        Long eventId = 2000L;
        Long ticketId = 2L;
        Long quantity = 125L;
        String phone = "010-1234-7895";
        Long applicantNum = 3L;
        String name = "하잉";

        CreateApplyRequestDTO createApplyRequestDTO = CreateApplyRequestDTO
                        .builder()
                        .ticketId(ticketId)
                        .eventId(eventId)
                        .quantity(quantity)
                        .phone(phone)
                        .applicantNum(applicantNum)
                        .name(name)
                        .build();

        // Act
        applyServiceImpl.createApply(userId, createApplyRequestDTO);

        // Assert
        verify(applyReposiroty, times(1)).insertApply(any());
    }

    @Test
    @DisplayName("[Fail - 없는 applyId] 행사 신청 취소")
    public void NonExistentIdExceptionOfCancelApplyTest(){
        Long applyId = 100L;
        when(applyReposiroty.CheckCancellationStatus(anyLong())).thenReturn(null);

        assertThrows(NonExistentIdException.class, () -> applyServiceImpl.cancelApply(applyId));
    }

    @Test
    @DisplayName("[Fail - 이미 신청 취소된 id] 행사 신청 취소")
    public void AlreadyCancelApplyExceptionOfCancelApplyTest(){
        Long applyId = 2L;
        when(applyReposiroty.CheckCancellationStatus(anyLong())).thenReturn(false);

        assertThrows(AlreadyCancelApplyException.class, () -> applyServiceImpl.cancelApply(applyId));
    }

    @Test
    @DisplayName("[Success] 행사 신청 취소")
    public void cancelApplySuccessTest(){
        Long applyId = 3L;
        when(applyReposiroty.CheckCancellationStatus(anyLong())).thenReturn(true);

        applyServiceImpl.cancelApply(applyId);

        verify(applyReposiroty, times(1)).cancelApply(anyLong());
    }

    @Test
    @DisplayName("[Success - null] 신청 목록 조회")
    public void findApplicationListNullSuccessTest(){
        Long userId = 2L;
        when(applyReposiroty.getApplicationList(anyLong())).thenReturn(new ArrayList<>());

        List<FindAppicaionListResponseDTO> response = applyServiceImpl.getApplicationList(userId);

        assertNull(response);
    }

    @Test
    @DisplayName("[Success]신청 목록 조회")
    public void findApplicationListTest(){
        Long userId = 2L;

        FindByUserIdDTO findByUserIdDTO = FindByUserIdDTO
                .builder()
                .ticketId(100L)
                .eventId(100L)
                .phone("010-7777-8888")
                .applicantNum(3L)
                .applyId(100L)
                .applyDate(LocalDateTime.now())
                .build();

        List<FindByUserIdDTO> findByUserIdDTOList = new ArrayList<>();
        findByUserIdDTOList.add(findByUserIdDTO);
        when(applyReposiroty.getApplicationList(anyLong())).thenReturn(findByUserIdDTOList);

        FindEventInfoResponseDTO eventInfo = FindEventInfoResponseDTO
                .builder()
                .ticketPrice(90000L)
                .image("src/src/image.jpg")
                .title("title1")
                .ticketName("ticketName")
                .eventEndAt(LocalDateTime.now().plusMonths(2))
                .eventId(100L)
                .ticketId(100L)
                .build();
        List<FindEventInfoResponseDTO> eventInfos = new ArrayList<>();
        eventInfos.add(eventInfo);
        when(apiService.apiRequest()).thenReturn(eventInfos);

        List<FindAppicaionListResponseDTO> response =  applyServiceImpl.getApplicationList(userId);

        assertNotNull(response);
        assertEquals("예약 완료", response.get(0).getStatus());
    }

    @Test
    @DisplayName("[API][Success - 0] 티켓별 현재 신청자 수 조회")
    public void getCurrentNumberOfApplicantsByTicketZeroSizeTest(){
        Long eventId = 1L;

        when(applyReposiroty.getCurrentNumberOfApplicantsByTicket(anyLong())).thenReturn(new ArrayList<>());

        List<FindUsingTicketResponseDTO> response = applyServiceImpl.getCurrentNumberOfApplicantsByTicket(eventId);
        assertEquals(0, response.size());
    }

    @Test
    @DisplayName("[API][Success] 티켓별 현재 신청자 수 조회")
    public void getUsingTicketListTest(){
        Long eventId = 1L;

        List<FindUsingTicketResponseDTO> result = new ArrayList<>();
        result.add(new FindUsingTicketResponseDTO());
        result.add(new FindUsingTicketResponseDTO());
        result.add(new FindUsingTicketResponseDTO());

        when(applyReposiroty.getCurrentNumberOfApplicantsByTicket(anyLong())).thenReturn(result);

        List<FindUsingTicketResponseDTO> response = applyServiceImpl.getCurrentNumberOfApplicantsByTicket(eventId);
        assertEquals(3, response.size());
    }

    @Test
    @DisplayName("[Success - null] 주최자 - 이벤트 별 참여자 목록 조회")
    public void findApplicantsListNullTest(){
        when(applyReposiroty.getApplicantsListByEvent(any())).thenReturn(new ArrayList<>());

        List<FindApplicantsListResposneDTO> response = applyServiceImpl.getApplicantsListByEvent(FindApplicantsListRequestDTO.builder().eventId(1L).build());

        assertNull(response);
    }

    @Test
    @DisplayName("[Success] 주최자 - 이벤트 별 참여자 목록 조회")
    public void findApplicantsListTest(){
        List<FindApplicantsListDTO> findApplicantsListDTOList = new ArrayList<>();
        findApplicantsListDTOList.add(FindApplicantsListDTO
                        .builder()
                        .applicantNum(3L)
                        .applyId(1L)
                        .date(LocalDateTime.now())
                        .state("결제 완료")
                        .name("호잇")
                        .phone("010-1111-2222")
                        .ticketId(2L)
                        .userId(3L)
                        .build());

        when(applyReposiroty.getApplicantsListByEvent(any())).thenReturn(findApplicantsListDTOList);

        List<FindEventInfoResponseDTO> findEventInfoResponseDTOList = new ArrayList<>();
        findEventInfoResponseDTOList.add(FindEventInfoResponseDTO
                        .builder()
                        .ticketPrice(90000L)
                        .image("src/src/image.jpg")
                        .title("title1")
                        .ticketName("ticketName")
                        .eventEndAt(LocalDateTime.of(2023, 11, 15, 00, 00))
                        .eventId(100L)
                        .ticketId(2L)
                        .build());
        when(apiService.apiRequest()).thenReturn(findEventInfoResponseDTOList);


        List<FindApplicantsListResposneDTO> response = applyServiceImpl.getApplicantsListByEvent(FindApplicantsListRequestDTO.builder().eventId(1L).build());

        assertEquals(1, response.size());
    }
}
