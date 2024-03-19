package com.eventty.businessservice.utils;

import com.eventty.businessservice.event.application.dto.request.EventCreateRequestDTO;
import com.eventty.businessservice.event.application.dto.request.EventUpdateRequestDTO;
import com.eventty.businessservice.event.application.dto.request.TicketCreateRequestDTO;
import com.eventty.businessservice.event.application.dto.request.TicketUpdateRequestDTO;
import com.eventty.businessservice.event.application.dto.response.*;
import com.eventty.businessservice.event.domain.entity.EventBasicEntity;
import com.eventty.businessservice.event.domain.entity.EventDetailEntity;
import com.eventty.businessservice.event.domain.entity.TicketEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class TestUtil {

    public static EventBasicWithoutHostInfoResponseDTO createFakeEventBasicDTO(Long id) {
        return EventBasicWithoutHostInfoResponseDTO.builder()
                .id(id)
                .hostId(123L)
                .title("Test Event")
                .eventStartAt(LocalDateTime.now())
                .eventEndAt(LocalDateTime.now().plusHours(2))
                .participateNum(50L)
                .location("Test Location")
                .category("sports")
                .isActive(true)
                .build();
    }

    public static EventBasicWithHostInfoResponseDTO createFakeEventBasicWithHostInfoDTO(Long id) {
        return EventBasicWithHostInfoResponseDTO.builder()
                .id(id)
                .hostId(123L)
                .title("Test Event")
                .eventStartAt(LocalDateTime.now())
                .eventEndAt(LocalDateTime.now().plusHours(2))
                .participateNum(50L)
                .location("Test Location")
                .category("sports")
                .isActive(true)
                .hostName("Test Host")
                .build();
    }

    public static ImageResponseDTO createFakeImageResponseDTO() {
        return ImageResponseDTO.builder()
                //.imageId(1L)
                .imagePathFromStorage("test/image/path")
                .imageOriginalFileName("test_image.jpg")
                .build();
    }

    public static EventDetailFindByIdResponseDTO createFakeEventDetailDTO() {
        // 가짜 이벤트 상세 정보 생성
        return EventDetailFindByIdResponseDTO.builder()
                .id(1L)
                .content("Test Event Detail")
                .applyStartAt(LocalDateTime.now())
                .applyEndAt(LocalDateTime.now().plusHours(2))
                .views(100L)
                .build();
    }

    public static List<TicketResponseDTO> createFakeTicketResponseDTOList() {
        // 가짜 티켓 목록 생성
        return Arrays.asList(
                TicketResponseDTO.builder()
                        .id(1L)
                        .eventId(1L)
                        .name("Test Ticket 1")
                        .price(10000L)
                        .quantity(100L)
                        .build(),
                TicketResponseDTO.builder()
                        .id(2L)
                        .eventId(1L)
                        .name("Test Ticket 2")
                        .price(20000L)
                        .quantity(200L)
                        .build()
        );
    }

    public static EventCreateRequestDTO createFakeEventCreateRequestDTO(){
        return EventCreateRequestDTO.builder()
                .title("Test Event")
                .eventStartAt(LocalDateTime.now())
                .eventEndAt(LocalDateTime.now().plusHours(2))
                .participateNum(50L)
                .location("Test Location")
                .category("sports")
                .content("Test Event Detail")
                .applyStartAt(LocalDateTime.now())
                .applyEndAt(LocalDateTime.now().plusHours(2))
                .tickets(Arrays.asList(
                        TicketCreateRequestDTO.builder()
                                .name("Test Ticket 1")
                                .price(10000L)
                                .quantity(100L)
                                .build(),
                        TicketCreateRequestDTO.builder()
                                .name("Test Ticket 2")
                                .price(20000L)
                                .quantity(200L)
                                .build()
                ))
                .build();
    }

    public static EventUpdateRequestDTO createFakeEventUpdateRequestDTO(){
        return EventUpdateRequestDTO.builder()
                .title("Updated Event")
                .category("sports")
                .content("Updated Event Detail")
                .build();
    }

    public static MockMultipartFile createFakeImageMultipartFile(){
        return new MockMultipartFile(
                "image",
                "test_image.jpg",
                "image/jpeg",
                "test image".getBytes()
        );
    }

    public static TicketUpdateRequestDTO createFakeTicketUpdateRequestDTO(){
        return TicketUpdateRequestDTO.builder()
                .name("Updated Ticket")
                .price(30000L)
                .build();
    }

    public static TicketEntity createFakeTicketEntity(Long id){
        return TicketEntity.builder()
                .id(id)
                .eventId(1L)
                .name("Test Ticket")
                .price(10000L)
                .quantity(100L)
                .is_deleted(false)
                .build();
    }

    public static List<EventBasicEntity> createFakeEventEntityList(Long size){
        return Arrays.asList(
                EventBasicEntity.builder()
                        .id(1L)
                        .hostId(123L)
                        .title("Test Event 1")
                        .eventStartAt(LocalDateTime.now())
                        .eventEndAt(LocalDateTime.now().plusHours(2))
                        .participateNum(50L)
                        .location("Test Location 1")
                        .category(1L)
                        .isActive(true)
                        .isDeleted(false)
                        .build(),
                EventBasicEntity.builder()
                        .id(2L)
                        .hostId(123L)
                        .title("Test Event 2")
                        .eventStartAt(LocalDateTime.now())
                        .eventEndAt(LocalDateTime.now().plusHours(2))
                        .participateNum(50L)
                        .location("Test Location 2")
                        .category(1L)
                        .isActive(true)
                        .isDeleted(false)
                        .build(),
                EventBasicEntity.builder()
                        .id(3L)
                        .hostId(123L)
                        .title("Test Event 3")
                        .eventStartAt(LocalDateTime.now())
                        .eventEndAt(LocalDateTime.now().plusHours(2))
                        .participateNum(50L)
                        .location("Test Location 3")
                        .category(1L)
                        .isActive(true)
                        .isDeleted(false)
                        .build()
        ).subList(0, size.intValue());
    }

    public static EventDetailEntity createFakeEventDetailEntity(Long eventId){
        return EventDetailEntity.builder()
                .id(eventId)
                .content("Test Event Detail")
                .applyStartAt(LocalDateTime.now())
                .applyEndAt(LocalDateTime.now().plusHours(2))
                .views(100L)
                .build();
    }

    public static EventDetailEntity createFakeEventDetail(Long eventId){
        return EventDetailEntity.builder()
                .id(eventId)
                .content("Test Event Detail")
                .applyStartAt(LocalDateTime.now())
                .applyEndAt(LocalDateTime.now().plusHours(2))
                .views(100L)
                .build();
    }

    public static List<TicketEntity> createFakeTicketList(List<Long> ticketIds){
        return Arrays.asList(
                TicketEntity.builder()
                        .id(ticketIds.get(0))
                        .eventId(1L)
                        .name("Test Ticket 1")
                        .price(10000L)
                        .quantity(100L)
                        .is_deleted(false)
                        .build(),
                TicketEntity.builder()
                        .id(ticketIds.get(1))
                        .eventId(1L)
                        .name("Test Ticket 2")
                        .price(20000L)
                        .quantity(200L)
                        .is_deleted(false)
                        .build()
        );
    }

    public static List<FullEventFindAllResponseDTO> createFullEventFindAllResponseDTOList(Long size){
        return Arrays.asList(
                FullEventFindAllResponseDTO.builder()
                        .id(1L)
                        .hostId(123L)
                        .title("Test Event 1")
                        .image("test/image/path")
                        .eventStartAt(LocalDateTime.now())
                        .eventEndAt(LocalDateTime.now().plusHours(2))
                        .participateNum(50L)
                        .location("Test Location 1")
                        .category("sports")
                        .isActive(true)
                        .build(),
                FullEventFindAllResponseDTO.builder()
                        .id(2L)
                        .hostId(123L)
                        .title("Test Event 2")
                        .image("test/image/path")
                        .eventStartAt(LocalDateTime.now())
                        .eventEndAt(LocalDateTime.now().plusHours(2))
                        .participateNum(50L)
                        .location("Test Location 2")
                        .category("sports")
                        .isActive(true)
                        .build(),
                FullEventFindAllResponseDTO.builder()
                        .id(3L)
                        .hostId(123L)
                        .title("Test Event 3")
                        .image("test/image/path")
                        .eventStartAt(LocalDateTime.now())
                        .eventEndAt(LocalDateTime.now().plusHours(2))
                        .participateNum(50L)
                        .location("Test Location 3")
                        .category("sports")
                        .isActive(true)
                        .build()
        ).subList(0, size.intValue());
    }

    public static FullEventFindByIdResponseDTO createFullEventFindByIdResponseDTO(Long id){
        return FullEventFindByIdResponseDTO.builder()
                .id(id)
                .hostId(123L)
                .title("Test Event")
                .image("test/image/path")
                .eventStartAt(LocalDateTime.now())
                .eventEndAt(LocalDateTime.now().plusHours(2))
                .participateNum(50L)
                .location("Test Location")
                .category("sports")
                .isActive(true)
                .content("Test Event Detail")
                .applyStartAt(LocalDateTime.now())
                .applyEndAt(LocalDateTime.now().plusHours(2))
                .views(100L)
                .tickets(Arrays.asList(
                        TicketResponseDTO.builder()
                                .id(1L)
                                .eventId(1L)
                                .name("Test Ticket 1")
                                .price(10000L)
                                .quantity(100L)
                                .build(),
                        TicketResponseDTO.builder()
                                .id(2L)
                                .eventId(1L)
                                .name("Test Ticket 2")
                                .price(20000L)
                                .quantity(200L)
                                .build()
                ))
                .build();
    }

}
