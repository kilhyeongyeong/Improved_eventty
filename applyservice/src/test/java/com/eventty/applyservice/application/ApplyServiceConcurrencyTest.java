//package com.eventty.applyservice.application;
//
//import com.eventty.applyservice.application.dto.FindByUserIdDTO;
//import com.eventty.applyservice.application.dto.request.CreateApplyRequestDTO;
//import com.eventty.applyservice.domain.ApplyReposiroty;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@Slf4j
//@SpringBootTest
//public class ApplyServiceConcurrencyTest {
//
//    private ApplyService applyService;
//    private ApplyReposiroty applyReposiroty;
//
//    @Autowired
//    public ApplyServiceConcurrencyTest(ApplyService applyService, ApplyReposiroty applyReposiroty){
//        this.applyService = applyService;
//        this.applyReposiroty = applyReposiroty;
//    }
//
//    @Test
//    @DisplayName("행사 신청 동시성 Test")
//    public void applicationConcurrencyTest() throws InterruptedException {
//        log.info("makeReservation 동시성 테스트 시작");
//
//        log.info("makeReservation 동시성 테스트 준비");
//        int numberOfThreads = 1000;
//        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
//        CountDownLatch latch = new CountDownLatch(numberOfThreads);
//
//        Long userId = 1000L;
//        Long eventId = 2000L;
//        Long ticketId = 2L;
//        Long quantity = 300L;
//        String phone = "010-1234-7895";
//        Long applicantNum = 2L;
//        String name = "길동씌";
//
//        CreateApplyRequestDTO createApplyRequestDTO = CreateApplyRequestDTO
//                .builder()
//                .ticketId(ticketId)
//                .eventId(eventId)
//                .quantity(quantity)
//                .phone(phone)
//                .applicantNum(applicantNum)
//                .name(name)
//                .build();
//
//        log.info("makeReservation 동시성 테스트 진행");
//        for(int i=0; i<numberOfThreads; i++){
//            service.execute(() -> {
//                applyService.createApply(userId, createApplyRequestDTO);
//                latch.countDown();
//            });
//        }
//
//        latch.await();
//
//        System.out.println("왜 안나와ㅡㅡ");
//        System.out.println("최종 갯수 : " + applyReposiroty.countTestCode());
//        assertEquals(quantity / applicantNum, applyReposiroty.countTestCode());
//    }
//}
