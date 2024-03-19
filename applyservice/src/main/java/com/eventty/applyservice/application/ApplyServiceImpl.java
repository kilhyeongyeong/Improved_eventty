package com.eventty.applyservice.application;

import com.eventty.applyservice.application.dto.CreateApplyDTO;
import com.eventty.applyservice.application.dto.FindApplicantsListDTO;
import com.eventty.applyservice.application.dto.FindByUserIdDTO;
import com.eventty.applyservice.application.dto.request.CreateApplyRequestDTO;
import com.eventty.applyservice.application.dto.request.FindApplicantsListRequestDTO;
import com.eventty.applyservice.application.dto.response.FindAppicaionListResponseDTO;
import com.eventty.applyservice.application.dto.response.FindApplicantsListResposneDTO;
import com.eventty.applyservice.application.dto.response.FindEventInfoResponseDTO;
import com.eventty.applyservice.application.dto.response.FindUsingTicketResponseDTO;
import com.eventty.applyservice.domain.ApplyReposiroty;
import com.eventty.applyservice.domain.code.ServerUri;
import com.eventty.applyservice.domain.exception.AlreadyCancelApplyException;
import com.eventty.applyservice.domain.exception.ExceedApplicantsException;
import com.eventty.applyservice.domain.exception.NonExistentIdException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplyServiceImpl implements ApplyService{

    private final ApplyReposiroty applyReposiroty;
    private final ServerUri serverUri;
    private final ApiService apiService;

    @Async
    @Override
    public void createApply(Long userId, CreateApplyRequestDTO createApplyRequestDTO) {
        // 신청전 유효성 검사
        validateBeforeApply(createApplyRequestDTO);

        applyReposiroty.insertApply(CreateApplyDTO
                .builder()
                .userId(userId)
                .eventId(createApplyRequestDTO.getEventId())
                .ticketId(createApplyRequestDTO.getTicketId())
                .applicantNum(createApplyRequestDTO.getApplicantNum())
                .phone(createApplyRequestDTO.getPhone())
                .name(createApplyRequestDTO.getName())
                .build());
    }

    @Async
    @Override
    public void cancelApply(Long applyId) {
        // 신청 취소전 유효성 검증
        validateBeforeCancel(applyId);

        applyReposiroty.cancelApply(applyId);
    }

    @Override
    public List<FindAppicaionListResponseDTO> getApplicationList(Long userId) {

        List<FindByUserIdDTO> applies = applyReposiroty.getApplicationList(userId);
        if(applies.size() == 0 || applies == null)
            return null;

        // Event Server로 부터 화면에 띄울 더 많은 정보 받아오기
        // Event Server로 보내기위한 TicketIds 중복 제거(Parameter 생성)
        Set<String> ticketIds = new HashSet<>();
        for(FindByUserIdDTO apply : applies){
            ticketIds.add(apply.getTicketId().toString());
        }
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("ticketIds", new ArrayList<>(ticketIds));


        // api request----------------------------------------------------
        apiService.uriPathSetting(serverUri.getEventServer(), serverUri.getGET_EVENT_TICKET_INFO());
        apiService.paramSetting(params);
        List<FindEventInfoResponseDTO> eventInfos = apiService.apiRequest();


        // service logic--------------------------------------------------
        Map<Long, FindEventInfoResponseDTO> eventInfoMap = new HashMap<>();
        for(FindEventInfoResponseDTO eventResponse : eventInfos){
            eventInfoMap.put(eventResponse.getTicketId(), eventResponse);
        }

        List<FindAppicaionListResponseDTO> responses = new ArrayList<>();
        for(FindByUserIdDTO apply : applies){
            FindEventInfoResponseDTO eventResponse = eventInfoMap.get(apply.getTicketId());

            log.debug("EventRespone : {}", eventResponse);

            // 예약 상태(status) & 예약or취소 날짜(date) 설정
            String status;
            LocalDateTime date = null;
            if(apply.getDeleteDate() != null) {
                status = "예약 취소";
                date = apply.getDeleteDate();
            }else if(eventResponse.getEventEndAt().isBefore(LocalDateTime.now())) {
                status = "행사 종료";
                date = apply.getApplyDate();
            }else {
                status = "예약 완료";
                date = apply.getApplyDate();
            }

            // return값 세팅
            responses.add(FindAppicaionListResponseDTO
                                .builder()
                                .ticketName(eventResponse.getTicketName())
                                .title(eventResponse.getTitle())
                                .image(eventResponse.getImage())
                                .ticketPrice(eventResponse.getTicketPrice() * apply.getApplicantNum())
                                .status(status)
                                .date(date)
                                .applyId(apply.getApplyId())
                                .applicantNum(apply.getApplicantNum())
                                .build());
        }
        
        return responses;
    }

    @Override
    public List<FindUsingTicketResponseDTO> getCurrentNumberOfApplicantsByTicket(Long eventId) {
        return applyReposiroty.getCurrentNumberOfApplicantsByTicket(eventId);
    }

    @Override
    public List<FindApplicantsListResposneDTO> getApplicantsListByEvent(FindApplicantsListRequestDTO findApplicantsListRequestDTO) {
        List<FindApplicantsListDTO> applies = applyReposiroty.getApplicantsListByEvent(findApplicantsListRequestDTO);

        if(applies.size() == 0 || applies == null) return null;

        // Event Server로 보내기위한 TicketIds 중복 제거(Parameter 생성)
        Set<String> ticketIds = new HashSet<>();
        for(FindApplicantsListDTO apply : applies){
            ticketIds.add(apply.getTicketId().toString());
        }
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("ticketIds", new ArrayList<>(ticketIds));

        // api request----------------------------------------------------
        apiService.uriPathSetting(serverUri.getEventServer(), serverUri.getGET_EVENT_TICKET_INFO());
        apiService.paramSetting(params);
        List<FindEventInfoResponseDTO> eventInfos = apiService.apiRequest();

        // ----------------------------------------------------------------

        Map<Long, FindEventInfoResponseDTO> eventInfoMap = new HashMap<>();
        for(FindEventInfoResponseDTO eventResponse : eventInfos){
            eventInfoMap.put(eventResponse.getTicketId(), eventResponse);
        }

        List<FindApplicantsListResposneDTO> applicantsList = new ArrayList<>();
        for(FindApplicantsListDTO applicant : applies){

            FindEventInfoResponseDTO eventInfo = eventInfoMap.get(applicant.getTicketId());

            Long price = eventInfo.getTicketPrice() * applicant.getApplicantNum();
            Long priceMin = findApplicantsListRequestDTO.getPriceMin();
            Long priceMax = findApplicantsListRequestDTO.getPriceMax();

            if(priceMin != null && priceMax != null){
                if(price < priceMin || price > priceMax) continue;
            }else if(priceMin != null){
                if(price < priceMin) continue;
            }else if(priceMax != null){
                if(price > priceMax) continue;
            }

            applicantsList.add(FindApplicantsListResposneDTO
                    .builder()
                    .applyId(applicant.getApplyId())
                    .date(applicant.getDate())
                    .name(applicant.getName())
                    .phone(applicant.getPhone())
                    .price(eventInfo.getTicketPrice() * applicant.getApplicantNum())
                    .state(applicant.getState())
                    .build());
        }

        return applicantsList;
    }
    //------------------------------------------ validation -----------------------------------------------//

    private void validateBeforeApply(CreateApplyRequestDTO createApplyRequestDTO){
        // 신청 인원수 확인
        Long count = applyReposiroty.getCurrentNumberOfApplicants(createApplyRequestDTO.getEventId());
        if(count != null && createApplyRequestDTO.getQuantity() <= count + createApplyRequestDTO.getApplicantNum())
            throw new ExceedApplicantsException(count + createApplyRequestDTO.getApplicantNum());
    }

    private void validateBeforeCancel(Long applyId){
        Boolean result = applyReposiroty.CheckCancellationStatus(applyId);
        // 유효하지 않는 appyId
        if(result == null)  throw new NonExistentIdException();
        // 이미 신청 취소한 applyId
        else if(!result)    throw new AlreadyCancelApplyException();
    }
}
