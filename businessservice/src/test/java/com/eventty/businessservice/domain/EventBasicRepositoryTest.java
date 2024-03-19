package com.eventty.businessservice.domain;

import com.eventty.businessservice.event.domain.entity.EventBasicEntity;
import com.eventty.businessservice.event.domain.repository.EventBasicRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = Replace.NONE) // 테스트용 인메모리 DB
@MybatisTest
public class EventBasicRepositoryTest {

    @Autowired
    private EventBasicRepository eventBasicRepository;

    @Test
    public void testSelectEventById() {
        // 테스트용 데이터베이스에 적절한 데이터가 있어야 함
        Long eventId = 1L;
        EventBasicEntity event = eventBasicRepository.selectEventById(eventId);
        assertNotNull(event);
        assertEquals(eventId, event.getId());
    }

    @Test
    public void testSelectAllEvents() {
        List<EventBasicEntity> events = eventBasicRepository.selectAllEvents();
        assertNotNull(events);
        assertTrue(events.size() > 0);
    }

    @Test
    public void testSelectEventsByHostId() {
        Long hostId = 1L;
        List<EventBasicEntity> events = eventBasicRepository.selectEventsByHostId(hostId);
        assertNotNull(events);
        assertTrue(events.size() > 0);
    }

    @Test
    public void testSelectEventsByIdList() {
        List<Long> idList = List.of(1L, 2L, 3L);
        List<EventBasicEntity> events = eventBasicRepository.selectEventsByIdList(idList);
        assertNotNull(events);
        assertTrue(events.size() > 0);
    }

    @Test
    public void testSelectEventsByCategory() {
        Long categoryId = 4L;
        List<EventBasicEntity> events = eventBasicRepository.selectEventsByCategory(categoryId);
        assertNotNull(events);
        assertTrue(events.size() > 0);
    }

    @Test
    public void testSelectEventsBySearch() {
        String keyword = "A";
        List<EventBasicEntity> events = eventBasicRepository.selectEventsBySearch(keyword);
        assertNotNull(events);
        assertTrue(events.size() > 0);
    }

}
