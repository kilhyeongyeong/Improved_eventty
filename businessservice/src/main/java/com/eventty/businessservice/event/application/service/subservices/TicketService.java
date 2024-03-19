package com.eventty.businessservice.event.application.service.subservices;

import com.eventty.businessservice.event.api.ApiClient;
import com.eventty.businessservice.event.api.dto.response.QueryAppliesCountResponseDTO;
import com.eventty.businessservice.event.application.dto.request.EventCreateRequestDTO;
import com.eventty.businessservice.event.application.dto.request.EventUpdateRequestDTO;
import com.eventty.businessservice.event.application.dto.request.TicketUpdateRequestDTO;
import com.eventty.businessservice.event.application.dto.response.TicketResponseDTO;
import com.eventty.businessservice.event.domain.entity.TicketEntity;
import com.eventty.businessservice.event.domain.exception.TicketNotFoundException;
import com.eventty.businessservice.event.domain.repository.TicketRepository;
import com.eventty.businessservice.event.presentation.response.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ApiClient apiClient;

    public List<TicketResponseDTO> findTicketsByEventId(Long eventId){

        // 티켓 정보 가져오기
        List<TicketEntity> ticketList = getTicketListIfExists(eventId);
        log.info("ticketList: {}", ticketList);

        // 해당 이벤트를 신청한 내역 리스트 가져오기 (Apply Server API 호출)
        // 각 티켓 정보에 Apply Server 로부터 받아온 신청된 티켓 갯수 정보를 더하여 반환
        // 신청된 티켓 갯수 정보가 없으면 0으로 반환

        ResponseEntity<ResponseDTO<List<QueryAppliesCountResponseDTO>>> appliesInfoResponse = apiClient.queryAppliesCountApi(eventId);
        if(appliesInfoResponse.getBody() == null){
            return ticketList.stream()
                    .map(TicketResponseDTO::from)
                    .toList();
        }
        List<QueryAppliesCountResponseDTO> appliesInfo = Objects.requireNonNull(appliesInfoResponse.getBody()).getSuccessResponseDTO().getData();
        log.info("appliesInfo : {}", appliesInfo);

        return ticketList.stream()
                .map(ticket -> TicketResponseDTO.from(
                        ticket,
                        getAppliesInfoByTicketId(appliesInfo, ticket.getId())))
                .toList();
    }

    public List<TicketResponseDTO> findTicketsByTicketIdList(List<Long> ticketIds){
        List<TicketEntity> ticketList = getTicketListIfExists(ticketIds);
        return ticketList.stream()
                .map(TicketResponseDTO::from)
                .toList();
    }

    public Long findEventIdByTicketId(Long ticketId){
        TicketEntity ticket = getTicketIfExists(ticketId);
        return ticket.getEventId();
    }

    public Long createTickets(Long eventId, EventCreateRequestDTO eventCreateRequest){
        eventCreateRequest.getTickets().stream()
                .map(ticketCreateRequest -> ticketCreateRequest.toEntity(eventId))
                .forEach(ticketRepository::insertTicket);

        return eventId;
    }

    public Long deleteTickets(Long eventId){
        // 삭제 전, 데이터 존재 확인
        List<TicketEntity> tickets = getTicketListIfExists(eventId);

        ticketRepository.deleteTicketsByEventId(eventId);
        return eventId;
    }

    public Long updateTickets(Long eventId, List<TicketUpdateRequestDTO> ticketUpdateRequestDTOList) {

        // 이벤트의 모든 티켓 불러오기
        List<TicketEntity> ticketList = getTicketListIfExists(eventId);

        // 기존 티켓의 총 참가자 수 계산
        Long totalParticipantNum = ticketList.stream()
                .map(TicketEntity::getQuantity)
                .reduce(0L, Long::sum);

        for (TicketUpdateRequestDTO ticketUpdateRequestDTO : ticketUpdateRequestDTOList) {
            // 업데이트 전, 데이터 존재 확인
            TicketEntity ticket = getTicketIfExists(ticketUpdateRequestDTO.getId());

            totalParticipantNum = totalParticipantNum - ticket.getQuantity() + ticketUpdateRequestDTO.getQuantity();

            ticket.update(ticketUpdateRequestDTO);
            ticketRepository.updateTicket(ticket);
        }

        return totalParticipantNum;
    }

    public Long updateTicket(Long ticketId, TicketUpdateRequestDTO ticketUpdateRequestDTO) {
        // 업데이트 전, 데이터 존재 확인
        TicketEntity ticket = getTicketIfExists(ticketId);

        ticket.update(ticketUpdateRequestDTO);

        ticketRepository.updateTicket(ticket);
        return ticketId;
    }

    public TicketEntity deleteTicket(Long ticketId) {
        // 삭제 전, 데이터 존재 확인
        TicketEntity ticket = getTicketIfExists(ticketId);

        ticketRepository.deleteTicketById(ticketId);
        return ticket;
    }

    private List<TicketEntity> getTicketListIfExists(Long eventId) {
        Optional<List<TicketEntity>> ticketOptional = Optional.ofNullable(ticketRepository.selectTicketByEventId(eventId));
        return ticketOptional.orElseThrow(() -> null); // 티켓 정보가 없으면 null 반환
    }

    private TicketEntity getTicketIfExists(Long ticketId) {
        Optional<TicketEntity> ticketOptional = Optional.ofNullable(ticketRepository.selectTicketById(ticketId));
        return ticketOptional.orElseThrow(() -> TicketNotFoundException.EXCEPTION);
    }

    private List<TicketEntity> getTicketListIfExists(List<Long> ticketIdList) {
        List<TicketEntity> ticketList = new ArrayList<>();
        for (Long ticketId : ticketIdList) {
            Optional<TicketEntity> ticketOptional = Optional.ofNullable(getTicketIfExists(ticketId));
            ticketOptional.ifPresent(ticketList::add); // 데이터가 존재하면 리스트에 추가
        }
        return ticketList;
    }

    private QueryAppliesCountResponseDTO getAppliesInfoByTicketId(List<QueryAppliesCountResponseDTO> appliesInfo, Long ticketId){
        // appliesInfoDTO 의 ticketId 와 ticketId 가 일치하는 데이터를 찾아 반환
        return appliesInfo.stream()
                .filter(appliesInfoDTO -> appliesInfoDTO.getTicketId().equals(ticketId))
                .findFirst()
                .orElse(null);
    }
}
