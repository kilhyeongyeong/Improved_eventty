package com.eventty.businessservice.domain;

import com.eventty.businessservice.event.domain.entity.TicketEntity;
import com.eventty.businessservice.event.domain.repository.TicketRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 테스트용 인메모리 DB
@MybatisTest
public class TicketRepositoryTest {

    @Autowired
    private TicketRepository ticketRepository;

    @Test
    @DisplayName("특정 이벤트에 할당된 티켓 조회 테스트")
    public void selectTicketByEventIdTest() {
        // given & when
        Long eventId = 5L;
        List<TicketEntity> tickets = ticketRepository.selectTicketByEventId(eventId);
        // then
        assertNotNull(tickets);
        assertTrue(tickets.size() > 1);
    }

    @Test
    @DisplayName("특정 티켓 조회 테스트")
    public void selectTicketByIdTest() {
        // given & when
        Long ticketId = 1L;
        TicketEntity ticket = ticketRepository.selectTicketById(ticketId);
        // then
        assertNotNull(ticket);
        assertEquals(ticketId, ticket.getId());
    }

}