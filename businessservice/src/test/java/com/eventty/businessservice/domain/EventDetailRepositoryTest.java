package com.eventty.businessservice.domain;

import com.eventty.businessservice.event.domain.entity.EventDetailEntity;
import com.eventty.businessservice.event.domain.repository.EventDetailRepository;
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
public class EventDetailRepositoryTest {

    @Autowired
    private EventDetailRepository eventDetailRepository;

    @Test
    public void testselectEventDetailById() {
        // 테스트용 데이터베이스에 적절한 데이터가 있어야 함
        Long eventId = 1L;
        EventDetailEntity event = eventDetailRepository.selectEventDetailById(eventId);
        assertNotNull(event);
        assertEquals(eventId, event.getId());
    }

    @Test
    public void testselectTop10EventsIdByViews() {
        List<Long> events = eventDetailRepository.selectTop10EventsIdByViews();
        assertNotNull(events);
        assertTrue(events.size() > 0);
    }

    @Test
    public void testselectTop10EventsIdByCreateDate() {
        List<Long> events = eventDetailRepository.selectTop10EventsIdByCreateDate();
        assertNotNull(events);
        assertTrue(events.size() > 0);
    }

    @Test
    public void testselectTop10EventsIdByApplyEndAt() {
        List<Long> events = eventDetailRepository.selectTop10EventsIdByApplyEndAt();
        assertNotNull(events);
        assertTrue(events.size() > 0);
    }
}
