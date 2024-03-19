package com.eventty.applyservice.domain;

import com.eventty.applyservice.application.dto.CreateApplyDTO;
import com.eventty.applyservice.application.dto.FindApplicantsListDTO;
import com.eventty.applyservice.application.dto.FindByUserIdDTO;
import com.eventty.applyservice.application.dto.request.FindApplicantsListRequestDTO;
import com.eventty.applyservice.application.dto.response.FindUsingTicketResponseDTO;
import org.junit.jupiter.api.BeforeEach;
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
public class ApplyRepositoryTest {
    @Autowired
    private ApplyReposiroty applyReposiroty;

    private Long applyId = null;

    @BeforeEach
    public void insert(){
        Long userId = 0L;
        Long eventId = 0L;
        Long ticktetId = 0L;
        Long applicantNum = 3L;
        String phone = "010-1234-1234";
        String name = "길똥씌";

        CreateApplyDTO createApplyDTO = CreateApplyDTO
                .builder()
                .userId(userId)
                .eventId(eventId)
                .ticketId(ticktetId)
                .applicantNum(applicantNum)
                .phone(phone)
                .name(name)
                .build();
        applyId = applyReposiroty.insertApply(createApplyDTO);
    }

    @Test
    @DisplayName("행사 신청")
    public void insertApplyTest(){
        Long userId = 0L;
        Long eventId = 0L;
        Long ticktetId = 0L;
        Long applicantNum = 3L;
        String phone = "010-1234-1234";
        String name = "홍길씌";

        CreateApplyDTO createApplyDTO = CreateApplyDTO
                .builder()
                .userId(userId)
                .eventId(eventId)
                .ticketId(ticktetId)
                .applicantNum(applicantNum)
                .phone(phone)
                .name(name)
                .build();

        Long response = applyReposiroty.insertApply(createApplyDTO);

        assertNotNull(response);
    }

    @Test
    @DisplayName("현재 신청 인원수")
    public void getCurrentNumberOfApplicantsTest(){
        Long eventId = 0L;

        Long response = applyReposiroty.getCurrentNumberOfApplicants(eventId);

        assertNotNull(response);
        assertEquals(3, response);
    }

    @Test
    @DisplayName("행사 신청 취소 - applyId가 없을 경우")
    public void CheckCancellationStatusNonExistAppyIdFailTest(){
        Long id = 100L;

        Boolean response = applyReposiroty.CheckCancellationStatus(id);

        assertNull(response);
    }

    @Test
    @DisplayName("행사 신청 취소")
    public void deleteApplyTest(){
        Long id = applyId;

        Long response = applyReposiroty.cancelApply(id);

        assertNotNull(response);
    }

    @Test
    @DisplayName("행사 조회")
    public void getApplicationListTest(){
        Long userId = 0L;

        List<FindByUserIdDTO> response = applyReposiroty.getApplicationList(userId);

        assertEquals(1, response.size());
        assertNull(response.get(0).getDeleteDate());
    }

    @Test
    @DisplayName("티켓별 현재 신청자 수 조회")
    public void getCurrentNumberOfApplicantsByTicketTest(){
        Long eventId = 0L;

        List<FindUsingTicketResponseDTO> response = applyReposiroty.getCurrentNumberOfApplicantsByTicket(eventId);

        assertNotEquals(0, response.size());
    }

    @Test
    @DisplayName("행사별 참여자 목록 조회 - 조건 X")
    public void getApplicantsListByEventTest(){
        FindApplicantsListRequestDTO findApplicantsListRequestDTO = FindApplicantsListRequestDTO
                .builder()
                .eventId(0L)
                .build();

        List<FindApplicantsListDTO> reponse = applyReposiroty.getApplicantsListByEvent(findApplicantsListRequestDTO);

        assertEquals(1, reponse.size());
    }

    @Test
    @DisplayName("행사별 참여자 목록 조회 - state 조건(예약취소)")
    public void asdf(){
        FindApplicantsListRequestDTO findApplicantsListRequestDTO = FindApplicantsListRequestDTO
                .builder()
                .state(2L)
                .eventId(0L)
                .build();

        List<FindApplicantsListDTO> reponse = applyReposiroty.getApplicantsListByEvent(findApplicantsListRequestDTO);

        assertEquals(0, reponse.size());
    }

}
