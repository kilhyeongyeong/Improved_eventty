package com.eventty.applyservice.presentation;

import com.eventty.applyservice.application.ApplyService;
import com.eventty.applyservice.application.dto.request.CreateApplyRequestDTO;
import com.eventty.applyservice.application.dto.request.FindApplicantsListRequestDTO;
import com.eventty.applyservice.application.dto.response.FindAppicaionListResponseDTO;
import com.eventty.applyservice.application.dto.response.FindApplicantsListResposneDTO;
import com.eventty.applyservice.application.dto.response.FindUsingTicketResponseDTO;
import com.eventty.applyservice.domain.annotation.ApiErrorCode;
import com.eventty.applyservice.domain.annotation.Permission;
import com.eventty.applyservice.domain.code.UserRole;
import com.eventty.applyservice.infrastructure.userContext.UserContextHolder;
import com.eventty.applyservice.presentation.dto.ResponseDTO;
import com.eventty.applyservice.presentation.dto.SuccessResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.eventty.applyservice.domain.code.ErrorCode.*;

@Slf4j
@RestController
@Tag(name = "Apply", description = "Apply Server - About Applies")
@RequiredArgsConstructor
@ApiResponses({
        @ApiResponse(content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
        @ApiResponse(content = @Content(schema = @Schema(implementation = SuccessResponseDTO.class)))
})
public class ApplyController {

    private final ApplyService applyService;
    private final String mydiaType = "application/json";

    /**
     * 행사 신청
     * @param createApplyRequestDTO
     * @return ResponseEntity<SuccessResponseDTO>
     */
    @PostMapping("/applies")
    @ApiResponse(responseCode = "201", description = "행사 신청")
    @ApiErrorCode({ALREADY_APPLY_USER, EXCEED_APPLICANTS})
    @Operation(summary = "행사 참여 신청", description = "참여할 행사를 신청합니다.")
    @Permission(Roles = {UserRole.USER})
    public ResponseEntity<SuccessResponseDTO> applyEvent(@RequestBody @Valid CreateApplyRequestDTO createApplyRequestDTO){

        Long userId = getUserIdBySecurityContextHolder();

        applyService.createApply(userId, createApplyRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 행사 신청 취소
     * @param applyId
     * @return
     */
    @DeleteMapping("/applies/{applyId}")
    @ApiResponse(responseCode = "200")
    @ApiErrorCode({NON_EXISTENT_ID, ALREADY_CANCELED_APPLY})
    @Operation(summary = "행사 참여 신청 취소", description = "기존에 신청했던 행사를 취소합니다.")
    @Permission(Roles = {UserRole.USER})
    public ResponseEntity<SuccessResponseDTO> cancelApply(@PathVariable("applyId")Long applyId){
        applyService.cancelApply(applyId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 참여자 - 행사 신청 내역 조회
     * @return
     */
    @GetMapping("/applies")
    @ApiResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = mydiaType,
                    array = @ArraySchema(schema = @Schema(implementation = FindAppicaionListResponseDTO.class))))
    @Operation(summary = "참여자 - 행사 신청 내역 조회", description = "참여자 본인이 신청한 모든 행사 신청 내역을 조회합니다.")
    @Permission(Roles = {UserRole.USER})
    public ResponseEntity<SuccessResponseDTO> getApplicationList(){
        Long userId = getUserIdBySecurityContextHolder();
        List<FindAppicaionListResponseDTO> response = applyService.getApplicationList(userId);
        return ResponseEntity.ok((response == null || response.size() == 0) ? null : SuccessResponseDTO.of(response));
    }

    /**
     * (API) 티켓별 현재 신청자 현황 조회 - 이벤트 상세조회시 사용
     * @param eventId
     * @return
     */
    @GetMapping("/api/applies/count")
    @ApiResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = mydiaType,
                    array = @ArraySchema(schema = @Schema(implementation = FindUsingTicketResponseDTO.class))
            )
    )
    @Operation(summary = "[API] 현재 신청자 수 조회", description = "이벤트 상세 조회시 현재 신청자 수를 조회하기 위해 값을 반환합니다.")
    public ResponseEntity<SuccessResponseDTO> getCurrentNumberOfApplicantsByTicket(@RequestParam Long eventId) {
        List<FindUsingTicketResponseDTO> responses = applyService.getCurrentNumberOfApplicantsByTicket(eventId);
        return ResponseEntity.ok(SuccessResponseDTO.of(responses));
    }

    /**
     * 주최자 - 행사 별 참여자 목록 조회
     * @param findApplicantsListRequestDTO
     * @return
     */
    @GetMapping("/applies/host")
    @ApiResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = mydiaType,
                    array = @ArraySchema(schema = @Schema(implementation = FindApplicantsListResposneDTO.class))
            )
    )
    @Operation(summary = "주최자 - 행사 별 참여자 목록", description = "Host가 본인이 주최한 행사별로 참여자 목록을 조회합니다.")
    public ResponseEntity<SuccessResponseDTO> getApplicantsListByEvent(FindApplicantsListRequestDTO findApplicantsListRequestDTO){
        List<FindApplicantsListResposneDTO> response = applyService.getApplicantsListByEvent(findApplicantsListRequestDTO);
        return ResponseEntity.ok(response == null || response.size() == 0 ? null : SuccessResponseDTO.of(response));
    }

    private Long getUserIdBySecurityContextHolder(){
        return Long.parseLong(UserContextHolder.getContext().getUserId());
    }
}
