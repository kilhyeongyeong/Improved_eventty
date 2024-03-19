package com.eventty.userservice.presentation;

import com.eventty.userservice.application.UserService;
import com.eventty.userservice.application.dto.request.*;
import com.eventty.userservice.application.dto.response.*;
import com.eventty.userservice.domain.annotation.ApiErrorCode;
import com.eventty.userservice.domain.annotation.ApiSuccessData;
import com.eventty.userservice.domain.annotation.Permission;
import com.eventty.userservice.domain.code.UserRole;
import com.eventty.userservice.infrastructure.context.UserContextHolder;
import com.eventty.userservice.presentation.dto.ResponseDTO;
import com.eventty.userservice.presentation.dto.SuccessResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.eventty.userservice.domain.code.ErrorCode.*;

@Slf4j
@RestController
@Tag(name = "User", description = "User Server - About Users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * (API) 회원가입
     *
     * @author khg
     * @param userCreateRequestDTO
     * @return ResponseEntity<SuccessResponseDTO>
     */
    @PostMapping("/api/users/me")
    @Operation(summary = "(API)ID, PW 제외한 내 정보 등록 (회원가입)")
    @ApiSuccessData(stateCode = "201")
    @ApiErrorCode({INVALID_INPUT_VALUE, INVALID_JSON})
    public ResponseEntity<SuccessResponseDTO> signUp(@RequestBody @Valid UserCreateRequestDTO userCreateRequestDTO){
        userService.signUp(userCreateRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * (API) 회원가입 - 소셜로그인
     * @param userOAuthCreateRequestDTO
     * @return
     */
    @PostMapping("/api/users/me/oauth")
    public ResponseEntity<?> authSignUpOrUpdate(@RequestBody @Valid UserOAuthCreateRequestDTO userOAuthCreateRequestDTO){
        UserSaveImageResponseDTO response = userService.oauthSignUp(userOAuthCreateRequestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response == null ? false : SuccessResponseDTO.of(response));
    }

    /**
     * 내 정보 조회
     * @return ResponseEntity<SuccessResponseDTO>
     */
    @GetMapping("/users/me")
    @Operation(summary = "내 정보 조회")
    @ApiSuccessData(UserFindByIdResponseDTO.class)
    @ApiErrorCode(USER_INFO_NOT_FOUND)
    @Permission(Roles = {UserRole.USER, UserRole.HOST})
    public ResponseEntity<SuccessResponseDTO> getMyInfo(){

        Long userId = getUserIdByUserContextHolder();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponseDTO.of(userService.getMyInfo(userId)));
    }

    /**
     * 내 정보 수정
     * @param userUpdateRequestDTO
     * @return ResponseEntity<SuccessResponseDTO>
     */
    @PostMapping(value = "/users/me", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "내 정보 수정")
    @ApiSuccessData()
    @ApiErrorCode({USER_INFO_NOT_FOUND, INVALID_JSON})
    @Permission(Roles = {UserRole.USER, UserRole.HOST})
    public ResponseEntity<SuccessResponseDTO> updateMyInfo(@ModelAttribute UserUpdateRequestDTO userUpdateRequestDTO,
                                                           @ModelAttribute UserImageUpdateRequestDTO userImageUpdateRequestDTO){

        Long userId = getUserIdByUserContextHolder();

        UserUpdateImageResponseDTO response = userService.updateMyInfo(userId, userUpdateRequestDTO, userImageUpdateRequestDTO);
        return ResponseEntity.ok(response == null ? null : SuccessResponseDTO.of(response));
    }

    /**
     * (API) 호스트 정보 반환
     * @param hostId
     * @return ResponseEntity<SuccessResponseDTO>
     */
    @GetMapping("/api/host")
    @Operation(summary = "(API) 호스트 정보 반환")
    @ApiSuccessData(HostFindByIdResposneDTO.class)
    public ResponseEntity<SuccessResponseDTO> apiGetHostInfo(@RequestParam Long hostId){

        HostFindByIdResposneDTO response =  userService.apiGetHostInfo(hostId);
        log.debug("(API) 호스트 정보 반환 Response : {}", response);
        return ResponseEntity.ok(SuccessResponseDTO.of(response));
    }

    /**
     * (API) User Image 반환
     * @return
     */
    @GetMapping("/api/image")
    @Operation(summary = "(API) User Image 반환")
    @ApiSuccessData(UserImageFindByIdResponseDTO.class)
    @Permission(Roles = {UserRole.USER, UserRole.HOST})
    public ResponseEntity<?> apiGetUserImage(){

        Long userId = getUserIdByUserContextHolder();

        UserImageFindByIdResponseDTO response = userService.apiGetUserImage(userId);
        log.debug("(API) User Image 반환 Response : {}", response);
        return ResponseEntity.ok(response == null ? false : SuccessResponseDTO.of(response));
    }

    /**
     * (API) 유저 정보 확인 (비밀번호 찾기)
     * @return
     */
    @PostMapping("/api/userId")
    @ApiSuccessData(value = Long.class, isArray = true)
    @ApiErrorCode(USER_INFO_NOT_FOUND)
    @Operation(summary = "(API) 유저 정보 확인 (비밀번호 찾기)")
    public ResponseEntity<?> apiCheckUserInfo(@RequestBody @Valid UserCheckRequestDTO userCheckRequestDTO){
        List<Long> response = userService.apiCheckUserInfo(userCheckRequestDTO);
        log.debug("(API) 유저 정보 확인 (비밀번호 찾기) Response : {}", response);
        return ResponseEntity.ok(response == null ? false : SuccessResponseDTO.of(response));
    }

    private Long getUserIdByUserContextHolder(){
        return Long.parseLong(UserContextHolder.getContext().getUserId());
    }

}
