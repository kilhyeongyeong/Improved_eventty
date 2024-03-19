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
    public Long getCurrentNumberOfApplicants(Long eventId);

    // 행사 취소 관련
    public Long cancelApply(Long applyId);
    public Boolean CheckCancellationStatus(Long applyId);

    // 행사 조회
    public List<FindByUserIdDTO> getApplicationList(Long userId);

    // 티켓별 현재 신청자 수 조회
    public List<FindUsingTicketResponseDTO> getCurrentNumberOfApplicantsByTicket(Long eventId);
    
    // 신청 참가자들 내역 조회
    public List<FindApplicantsListDTO> getApplicantsListByEvent(FindApplicantsListRequestDTO request);

    // 동시성 Test
    public Long countTestCode();
}
