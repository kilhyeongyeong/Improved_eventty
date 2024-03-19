package com.eventty.applyservice.domain;

import com.eventty.applyservice.application.dto.CreateApplyDTO;
import com.eventty.applyservice.application.dto.FindApplicantsListDTO;
import com.eventty.applyservice.application.dto.FindByUserIdDTO;
import com.eventty.applyservice.application.dto.request.FindApplicantsListRequestDTO;
import com.eventty.applyservice.application.dto.response.FindUsingTicketResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApplyReposiroty {

    // 행사 신청 관련
    public Long insertApply(CreateApplyDTO createApplyDTO);
    public Long getApplyNum(Long eventId);

    // 행사 취소 관련
    public Long deleteApply(Long applyId);
    public Boolean deleteCheck(Long applyId);

    // 행사 조회 관련
    public List<FindByUserIdDTO> findByUserId(Long userId);
    public List<FindUsingTicketResponseDTO> findByEventIdGroupByTicket(Long eventId);
    
    // 신청 참가자들 내역 조회
    public List<FindApplicantsListDTO> findByEventId(FindApplicantsListRequestDTO request);

    // 동시성 Test
    public Long countTestCode();
}
