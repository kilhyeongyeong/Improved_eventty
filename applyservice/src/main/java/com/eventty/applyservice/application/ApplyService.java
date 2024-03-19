package com.eventty.applyservice.application;

import com.eventty.applyservice.application.dto.request.CreateApplyRequestDTO;
import com.eventty.applyservice.application.dto.request.FindApplicantsListRequestDTO;
import com.eventty.applyservice.application.dto.response.FindAppicaionListResponseDTO;
import com.eventty.applyservice.application.dto.response.FindApplicantsListResposneDTO;
import com.eventty.applyservice.application.dto.response.FindUsingTicketResponseDTO;

import java.util.List;

public interface ApplyService {

    public void createApply(Long userId, CreateApplyRequestDTO applyEventRequestDTO);

    public void cancelApply(Long applyId);

    public List<FindAppicaionListResponseDTO> findApplicationList(Long userId);

    public List<FindUsingTicketResponseDTO> getUsingTicketList(Long eventId);

    public List<FindApplicantsListResposneDTO> findApplicantsList(FindApplicantsListRequestDTO findApplicantsListRequestDTO);
}
