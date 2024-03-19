package com.eventty.businessservice.event.presentation.controller;

import com.eventty.businessservice.event.application.dto.request.EventCreateRequestDTO;
import com.eventty.businessservice.event.application.dto.request.EventUpdateRequestDTO;
import com.eventty.businessservice.event.application.dto.request.TicketUpdateRequestDTO;
import com.eventty.businessservice.event.application.dto.response.FullEventFindAllResponseDTO;
import com.eventty.businessservice.event.application.dto.response.FullEventFindByIdResponseDTO;
import com.eventty.businessservice.event.application.dto.response.EventInfoApiResponseDTO;
import com.eventty.businessservice.event.application.service.Facade.EventService;
import com.eventty.businessservice.event.domain.Enum.Category;
import com.eventty.businessservice.event.domain.Enum.ErrorCode;
import com.eventty.businessservice.event.domain.Enum.UserRole;
import com.eventty.businessservice.event.domain.annotation.ApiErrorCode;
import com.eventty.businessservice.event.domain.annotation.ApiSuccessData;
import com.eventty.businessservice.event.domain.annotation.Permission;
import com.eventty.businessservice.event.infrastructure.contextholder.UserContextHolder;
import com.eventty.businessservice.event.presentation.response.SuccessResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static com.eventty.businessservice.event.domain.Enum.SuccessCode.*;

@RestController
@Validated
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Event", description = "Event API")
public class EventController {

    private final EventService eventService;

    /**
     * (ALL) 전체 이벤트 리스트를 가져옵니다.
     *
     * @return ResponseEntity<SuccessResponseDTO> 전체 이벤트 리스트 응답
     */
    @GetMapping( "/events")
    @Operation(summary = "(ALL) 전체 이벤트 리스트를 가져옵니다.")
    @ApiSuccessData(value = FullEventFindAllResponseDTO.class, array = true)
    @ApiErrorCode(ErrorCode.EVENT_NOT_FOUND)
    public ResponseEntity<SuccessResponseDTO<List<FullEventFindAllResponseDTO>>> findAllEvents()
    {
        List<FullEventFindAllResponseDTO> events = eventService.findAllEvents();

        return ResponseEntity
                .status(GET_EVENT_INFO_SUCCESS.getStatus())
                .body(SuccessResponseDTO.of(events));
    }

    /**
     * (ALL) 이벤트를 조회수, 최신순, 이벤트 마감일 가까운 순으로 각 10개 씩 가져옵니다.
     *
     * @return ResponseEntity<SuccessResponseDTO> 이벤트 리스트 응답
     */
    @GetMapping("/events/top10")
    @Operation(summary = "(ALL) 이벤트를 조회수, 최신순, 이벤트 마감일 가까운 순으로 각 10개 씩 가져옵니다.")
    @ApiSuccessData(value = FullEventFindAllResponseDTO.class, array = true)
    @ApiErrorCode(ErrorCode.EVENT_NOT_FOUND)
    public ResponseEntity<SuccessResponseDTO<Map<String, List<FullEventFindAllResponseDTO>>>> findEventsBySpecial() {

        Map<String, List<FullEventFindAllResponseDTO>> events = eventService.findTop10Events();

        return ResponseEntity
                .status(GET_EVENT_INFO_SUCCESS.getStatus())
                .body(SuccessResponseDTO.of(events));
    }

    /**
     * (ALL) 이벤트를 카테고리별로 조회합니다.
     *
     * @param category 조회할 카테고리
     * @return ResponseEntity<SuccessResponseDTO> 카테고리별 이벤트 리스트 응답
     */
    @GetMapping( "/events/category/{category}")
    @Operation(summary = "(ALL) 이벤트를 카테고리별로 조회합니다.")
    @ApiSuccessData(value = FullEventFindAllResponseDTO.class, array = true)
    @ApiErrorCode(ErrorCode.EVENT_NOT_FOUND)
    public ResponseEntity<SuccessResponseDTO<List<FullEventFindAllResponseDTO>>> findEventsByCategory(
            @PathVariable Category category
    ) {
        List<FullEventFindAllResponseDTO> events = eventService.findEventsByCategory(category);

        return ResponseEntity
                .status(GET_EVENT_INFO_SUCCESS.getStatus())
                .body(SuccessResponseDTO.of(events));
    }

    /**
     * (ALL) 이벤트를 키워드로 검색하여, 최신순으로 가져옵니다.
     *
     * @param keyword 검색할 키워드
     * @return ResponseEntity<SuccessResponseDTO> 검색 결과 이벤트 리스트 응답
     */
    @GetMapping( "/events/search")
    @Operation(summary = "(ALL) 이벤트를 키워드로 검색하여, 최신순으로 가져옵니다.")
    @ApiSuccessData(value = FullEventFindAllResponseDTO.class, array = true)
    @ApiErrorCode(ErrorCode.EVENT_NOT_FOUND)
    public ResponseEntity<SuccessResponseDTO<List<FullEventFindAllResponseDTO>>> findEventsBySearch(
            @RequestParam String keyword
    ) {
        List<FullEventFindAllResponseDTO> events = eventService.findEventsBySearch(keyword);

        return ResponseEntity
            .status(GET_EVENT_INFO_SUCCESS.getStatus())
            .body(SuccessResponseDTO.of(events));
    }

    /**
     * (ALL) 특정 호스트가 등록한 이벤트 리스트를 가져옵니다.
     *
     * @param hostId 호스트의 ID
     * @return ResponseEntity<SuccessResponseDTO> 호스트의 이벤트 리스트 응답
     */
    @GetMapping( "/events/host/{hostId}")
    @Operation(summary = "(ALL) 특정 호스트가 등록한 이벤트 리스트를 가져옵니다.")
    @ApiSuccessData(value = FullEventFindAllResponseDTO.class, array = true)
    @ApiErrorCode(ErrorCode.EVENT_NOT_FOUND)
    public ResponseEntity<SuccessResponseDTO<List<FullEventFindAllResponseDTO>>> findEventsByHostId(
            @PathVariable @Min(1) Long hostId
    ) {

        List<FullEventFindAllResponseDTO> events = eventService.findEventsByHostId(hostId);

        return ResponseEntity
            .status(GET_EVENT_INFO_SUCCESS.getStatus())
            .body(SuccessResponseDTO.of(events));
    }

    /**
     * (ALL) 특정 이벤트의 상세 정보를 가져옵니다.
     *
     * @param eventId 조회할 이벤트의 ID
     * @return ResponseEntity<SuccessResponseDTO> 이벤트 상세 정보 응답
     */
    @GetMapping( "/events/{eventId}")
    @Operation(summary = "(ALL) 특정 이벤트의 상세 정보를 가져옵니다.")
    @ApiSuccessData(FullEventFindByIdResponseDTO.class)
    @ApiErrorCode(ErrorCode.EVENT_NOT_FOUND)
    public ResponseEntity<SuccessResponseDTO<FullEventFindByIdResponseDTO>> findEventById(
            @PathVariable @Min(1) Long eventId
    ){
        // 상세 페이지에서 Host 정보를 위해 User Server API 호출
        FullEventFindByIdResponseDTO event = eventService.findEventById(eventId);

        return ResponseEntity
                .status(GET_EVENT_INFO_SUCCESS.getStatus())
                .body(SuccessResponseDTO.of(event));
    }

    /**
     * (HOST) 호스트 본인이 주최한 이벤트 리스트를 조회합니다.
     *
     * @return 이벤트 리스트 응답
     */
    @GetMapping( "/events/registered")
    @Operation(summary = "(HOST) 호스트 본인이 주최한 이벤트 리스트를 조회합니다.")
    @ApiSuccessData(value = FullEventFindAllResponseDTO.class, array = true)
    @ApiErrorCode(ErrorCode.EVENT_NOT_FOUND)
    @Permission(Roles = {UserRole.HOST})
    public ResponseEntity<SuccessResponseDTO<List<FullEventFindAllResponseDTO>>> findMyEvents() {

        Long hostId = getUserIdBySecurityContextHolder();
        List<FullEventFindAllResponseDTO> events = eventService.findEventsByHostId(hostId);

        return ResponseEntity
                .status(GET_EVENT_INFO_SUCCESS.getStatus())
                .body(SuccessResponseDTO.of(events));
    }

    /**
     * (HOST) 호스트가 새로운 이벤트와 이벤트에 해당되는 티켓을 생성합니다.
     *
     * @param eventCreateRequestDTO 이벤트 및 티켓 생성 요청 데이터
     * @param image                 이벤트 이미지 (선택 사항)
     * @return 생성된 이벤트의 ID 응답
     */
    @PostMapping("/events")
    @Operation(summary = "(HOST) 호스트가 새로운 이벤트와 이벤트에 해당되는 티켓을 생성합니다.")
    @ApiSuccessData(value = Long.class)
    @Permission(Roles = {UserRole.HOST})
    public ResponseEntity<SuccessResponseDTO<Long>> createEvent(
            @RequestPart(value = "eventInfo") EventCreateRequestDTO eventCreateRequestDTO,
            @RequestPart(value = "image", required = false) MultipartFile image
    ){
        Long hostId = getUserIdBySecurityContextHolder();
        Long newEventId = eventService.createEvent(hostId, eventCreateRequestDTO, image);

        return ResponseEntity
            .status(CREATE_EVENT_SUCCESS.getStatus())
            .body(SuccessResponseDTO.of(newEventId));
    }

    /**
     * (HOST) 호스트가 본인이 주최한 이벤트의 정보와 티켓의 정보를 수정합니다.
     *
     * @param eventId               수정할 이벤트의 ID
     * @param eventUpdateRequestDTO 수정할 이벤트 정보
     * @return 수정된 이벤트의 ID 응답
     */
    @PatchMapping(value = "/events/{eventId}")
    @Operation(summary = "(HOST) 호스트가 본인이 주최한 이벤트의 정보와 티켓의 정보를 수정합니다.")
    @ApiSuccessData(value = Long.class)
    @ApiErrorCode(ErrorCode.EVENT_NOT_FOUND)
    @Permission(Roles = {UserRole.HOST})
    public ResponseEntity<SuccessResponseDTO<Long>> updateEvent(
            @PathVariable @Min(1) Long eventId,
            @RequestBody @Valid EventUpdateRequestDTO eventUpdateRequestDTO
    ){
        Long hostId = getUserIdBySecurityContextHolder();
        Long updatedEventId = eventService.updateEvent(hostId, eventId, eventUpdateRequestDTO);

        return ResponseEntity
            .status(UPDATE_EVENT_SUCCESS.getStatus())
            .body(SuccessResponseDTO.of(updatedEventId));
    }

    /**
     * (HOST) 호스트가 본인이 주최한 이벤트를 삭제합니다.
     *
     * @param eventId 삭제할 이벤트의 ID
     * @return 삭제된 이벤트의 ID 응답
     */
    @DeleteMapping("/events/{eventId}")
    @Operation(summary = "(HOST) 호스트가 본인이 주최한 이벤트를 삭제합니다.")
    @ApiSuccessData(value = Long.class)
    @ApiErrorCode(ErrorCode.EVENT_NOT_FOUND)
    @Permission(Roles = {UserRole.HOST})
    public ResponseEntity<SuccessResponseDTO<?>> deleteEvent(
            @PathVariable @Min(1) Long eventId
    ){
        Long hostId = getUserIdBySecurityContextHolder();
        Long deleteEventId = eventService.deleteEvent(hostId, eventId);

        return ResponseEntity
            .status(DELETE_EVENT_SUCCESS.getStatus())
            .body(SuccessResponseDTO.of(deleteEventId));
    }

    /**
     * 티켓 API
     * - 이후 별도의 티켓 수정 및 삭제 API 가 필요할 때 사용 에정.
     * - 현재는 이벤트 수정 및 삭제 시 티켓도 함께 수정 및 삭제 되도록 구현되어 있으므로 사용하지 않음.
     */

    /**
     * (HOST) 호스트가 본인이 주최한 이벤트의 티켓 정보를 수정합니다.
     *
     * @param ticketId               수정할 티켓의 ID
     * @param ticketUpdateRequestDTO 수정할 티켓 정보
     * @return 수정된 티켓의 ID 응답
     */
    @PatchMapping(value = "/events/ticket/{ticketId}")
    @Operation(summary = "(HOST) 호스트가 본인이 주최한 이벤트의 티켓 정보를 수정합니다.")
    @ApiSuccessData(value = Long.class)
    @ApiErrorCode(ErrorCode.TICKET_NOT_FOUND)
    @Permission(Roles = {UserRole.HOST})
    public ResponseEntity<SuccessResponseDTO<Long>> updateTicket(
            @PathVariable Long ticketId,
            @RequestBody @Valid TicketUpdateRequestDTO ticketUpdateRequestDTO
    ){
        Long hostId = getUserIdBySecurityContextHolder();
        Long updatedTicketId = eventService.updateTicket(hostId, ticketId, ticketUpdateRequestDTO);

        return ResponseEntity
            .status(UPDATE_TICKET_SUCCESS.getStatus())
            .body(SuccessResponseDTO.of(updatedTicketId));
    }

    /**
     * (HOST) 호스트가 본인이 주최한 이벤트의 티켓을 삭제합니다.
     *
     * @param ticketId 삭제할 티켓의 ID
     * @return 삭제된 티켓의 ID 응답
     */
    @DeleteMapping("/events/ticket/{ticketId}")
    @Operation(summary = "(HOST) 호스트가 본인이 주최한 이벤트의 티켓을 삭제합니다.")
    @ApiSuccessData(value = Long.class)
    @ApiErrorCode(ErrorCode.TICKET_NOT_FOUND)
    @Permission(Roles = {UserRole.HOST})
    public ResponseEntity<SuccessResponseDTO<Long>> deleteTicket(
            @PathVariable @Min(1) Long ticketId
    ){
        Long hostId = getUserIdBySecurityContextHolder();
        Long deleteTicketId = eventService.deleteTicket(hostId, ticketId);

        return ResponseEntity
            .status(DELETE_TICKET_SUCCESS.getStatus())
            .body(SuccessResponseDTO.of(deleteTicketId));
    }

    /**
     * (서버간 통신 API) 이벤트와 티켓 정보 반환
     * - Apply Server 로부터 요청을 받아, 이벤트와 티켓 정보를 반환하는 API
     * - 유저가 신청한 티켓 ID 리스트를 파라미터로 보내면, 해당 티켓과 이벤트의 정보를 조회하기 위한 용도.
     *
     * @param ticketIds 신청한 티켓 ID 리스트
     * @return ResponseEntity<SuccessResponseDTO> 이벤트 정보 응답
     */
    @GetMapping("/api/events")
    @Operation(summary = "Apply 서버로부터의 요청 처리 - 신청한 티켓 ID 리스트를 파라미터로 보내면, 해당 티켓과 이벤트의 정보를 조회하기 위한 용도.")
    @ApiSuccessData(value = EventInfoApiResponseDTO.class, array = true)
    @Permission(Roles = {UserRole.USER, UserRole.HOST})
    public ResponseEntity<SuccessResponseDTO<List<EventInfoApiResponseDTO>>> findEventInfoApi(
            @RequestParam List<Long> ticketIds
    ) {
        List<EventInfoApiResponseDTO> result = eventService.findByTicketIds(ticketIds);

        return ResponseEntity
                .status(FIND_EVENT_INFO_API.getStatus())
                .body(SuccessResponseDTO.of(result));
    }

    private Long getUserIdBySecurityContextHolder(){
        return Long.parseLong(UserContextHolder.getContext().getUserId());
    }

}
