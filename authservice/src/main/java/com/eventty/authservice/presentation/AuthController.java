package com.eventty.authservice.presentation;

import com.eventty.authservice.applicaiton.dto.CsrfTokenDTO;
import com.eventty.authservice.applicaiton.dto.LoginSuccessDTO;
import com.eventty.authservice.applicaiton.dto.SessionTokensDTO;
import com.eventty.authservice.applicaiton.service.Facade.UserService;
import com.eventty.authservice.domain.Enum.OAuth;
import com.eventty.authservice.domain.Enum.SessionAttr;
import com.eventty.authservice.domain.Enum.UserRole;
import com.eventty.authservice.global.Enum.SuccessCode;
import com.eventty.authservice.global.response.ResponseDTO;
import com.eventty.authservice.global.response.SuccessResponseDTO;
import com.eventty.authservice.infrastructure.annotation.ApiSuccessData;
import com.eventty.authservice.infrastructure.utils.CookieUtils;
import com.eventty.authservice.presentation.dto.request.*;
import com.eventty.authservice.presentation.dto.response.AuthenticationDetailsResponseDTO;
import com.eventty.authservice.presentation.dto.response.EmailFindResponseDTO;
import com.eventty.authservice.presentation.dto.response.LoginResponseDTO;
import com.eventty.authservice.presentation.dto.response.PWFindResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
@Slf4j
@Tag(name= "Auth", description = "Auth API")
public class AuthController {

    private final String HEADER_CSRF = "X-Csrf-Token";

    private final UserService userService;
    private final CookieUtils cookieUtils;

    /**
     * 회원가입
     */
    @PostMapping("/me/{userRole}")
    public ResponseEntity<Void> createUser(@Valid @RequestBody FullUserCreateRequestDTO userCreateRequestDTO,
                                           @PathVariable("userRole") UserRole userRole) {
        log.debug("Current Position: Controller :: 회원가입");

        // 유저 생성
        userService.createUser(userCreateRequestDTO, userRole);

        return ResponseEntity
                .status(SuccessCode.USER_CREATED.getStatus())
                .body(null);
    }

    /**
     * 이메일 검증
     */
    @PostMapping("/email")
    public ResponseEntity<Void> isDuplicateEmail(@Valid @RequestBody IsUserDuplicateRequestDTO isUserDuplicateRequestDTO) {
        log.debug("Current Position: Controller :: 이메일 검증");

        // 이메일 검증
        userService.validateEmailNotDuplicated(isUserDuplicateRequestDTO.getEmail());

        return ResponseEntity
                .status(SuccessCode.IS_OK.getStatus())
                .body(null);
    }

    /**
     * 로그인
     *
     * HttpServletResponse httpServletResponse를 사용해서 쿠키에 담아도 되지만,
     * ResponseEntity만 사용하여 응답의 명시성을 높이는 방향으로 진행했습니다.
     */
    @PostMapping("/login")
    @ApiSuccessData(LoginResponseDTO.class)
    public ResponseEntity<SuccessResponseDTO<LoginResponseDTO>> login(@Valid @RequestBody UserLoginRequestDTO userLoginRequestDTO) {
        log.debug("Current Position: Controller :: 로그인");

        // 로그인
        LoginSuccessDTO loginSuccessDTO = userService.login(userLoginRequestDTO);

        // 토큰들 쿠키에 담는 작업 및 헤더에 담는 작업과 body에 필요한 데이터 담아서 보내기
        return processLoginPostActions(loginSuccessDTO);
    }

    /**
     * 소셜 로그인
     */

    @PostMapping("/oauth/login/{socialName}")
    public ResponseEntity<SuccessResponseDTO<LoginResponseDTO>> oauthLogin(@Valid @RequestBody OAuthLoginRequestDTO oAuthLoginRequestDTO,
                                                                           @PathVariable("socialName") OAuth oAuth) {
        log.debug("Current Position: Controller :: OAuth 로그인");

        // 로그인
        LoginSuccessDTO loginSuccessDTO = userService.oauthLogin(oAuthLoginRequestDTO, oAuth.getSocialName());

        // 토큰들 쿠키에 담는 작업 및 헤더에 담는 작업과 body에 필요한 데이터 담아서 보내기
        return processLoginPostActions(loginSuccessDTO);
    }

    /**
     * 회원 탈퇴(Soft Delete)
     */
    @DeleteMapping("/me")
    // @Permission(Roles = {UserRole.USER})
    public ResponseEntity<Void> delete(HttpServletRequest request) {
        log.debug("Current Position: Controller :: 회원 탈퇴");

        SessionTokensDTO sessionTokensDTO = cookieUtils.getSessionTokens(request);
        String csrfToken = cookieUtils.getCsrfToken(request);

        // 유저 삭제
        userService.deleteUser(sessionTokensDTO, csrfToken);

        // 사용자의 브라우저에 저장되어 있는 토큰 삭제
        ResponseCookie deleteAccessToken = cookieUtils.deleteAccessTokenCookie();
        ResponseCookie deleteRefreshToken = cookieUtils.deleteRefreshTokenCookie();

        return ResponseEntity
                .status(SuccessCode.IS_OK.getStatus())
                .header(HttpHeaders.SET_COOKIE, deleteAccessToken.toString())
                .header(HttpHeaders.SET_COOKIE, deleteRefreshToken.toString())
                .body(null);
    }

    /**
     * 유저에 대한 검증
     */
    @PostMapping("/api/authenticate/user")
    public ResponseEntity<SuccessResponseDTO<AuthenticationDetailsResponseDTO>> authenticataeUser(@RequestBody UserAuthenticateRequestDTO UserAuthenticateRequestDTO) {
        log.debug("Current Position: Controller :: 회원 검증");

        // 회원 검증
        AuthenticationDetailsResponseDTO authenticationDetailsResponseDTO = userService.authenticateUser(UserAuthenticateRequestDTO);

        return ResponseEntity
                .status(SuccessCode.IS_OK.getStatus())
                .body(SuccessResponseDTO.of(authenticationDetailsResponseDTO));
    }
    /**
     * 유저 로그아웃
     *
     * 이 경우, 인증 서버가 endPoint이므로 게이트웨이에서 Response를 수정할 것으로 고려하는 것이 아닌 직접 Response에 담아서 보내는 방향으로 진행
     * -> 게이트웨이와 인증 서버간의 추가적인 결합이 발생하지 않는다. (즉, 인증 서버가 엔드포인트인 경우 게이트웨이에서 JWT 필터를 걸 필요가 X
     * => 만약에 기존 로직과 동일하게 진행할 경우 최종 Response의 Body에 토큰의 정보가 담기게 되므로 게이트웨이는 이것을 뺴고 헤더에 담아서 보내야되는 추가적인 로직이 발생.
     * 따라서, 결합도가 높아짐
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        log.debug("Current Position: Controller :: 회원 로그아웃");

        SessionTokensDTO sessionTokensDTO = cookieUtils.getSessionTokens(request);
        String csrfToken = cookieUtils.getCsrfToken(request);

        userService.logout(sessionTokensDTO, csrfToken);

        ResponseCookie deleteAccessToken = cookieUtils.deleteAccessTokenCookie();
        ResponseCookie deleteRefreshToken = cookieUtils.deleteRefreshTokenCookie();

        return ResponseEntity
                .status(SuccessCode.IS_OK.getStatus())
                .header(HttpHeaders.SET_COOKIE, deleteAccessToken.toString())
                .header(HttpHeaders.SET_COOKIE, deleteRefreshToken.toString())
                .body(null);
    }

    /**
     * 비밀번호 변경
     */
    @PostMapping("/change/password")
    public ResponseEntity<Void> changePW(@Valid @RequestBody PWChangeRequestDTO changePWRequestDTO, HttpServletRequest request) {
        log.debug("Current Position: Controller:: 회원 비밀번호 변경");

        SessionTokensDTO sessionTokensDTO = cookieUtils.getSessionTokens(request);
        String csrfToken = cookieUtils.getCsrfToken(request);

        CsrfTokenDTO csrfTokenDTO = userService.changePW(changePWRequestDTO, sessionTokensDTO, csrfToken);

        return ResponseEntity
                .status(SuccessCode.IS_OK.getStatus())
                .header(HEADER_CSRF, csrfTokenDTO.value())
                .body(null);
    }

    /**
     * 이메일 찾기
     */
    @PostMapping("/find/email")
    public ResponseEntity<SuccessResponseDTO<List<EmailFindResponseDTO>>> findEmail(@Valid @RequestBody EmailFindRequestDTO emailFindRequestDTO) {
        log.debug("Current Position: Controller:: 이메일 찾기");

        List<EmailFindResponseDTO> emailFindResponseDTOList =  userService.queryFindEmail(emailFindRequestDTO);

        return ResponseEntity
                .status(SuccessCode.IS_OK.getStatus())
                .body(SuccessResponseDTO.of(emailFindResponseDTOList));
    }

    /**
     * 패스워드 변경 이전 검증 작업
     */
    @PostMapping("/find/password")
    public ResponseEntity<SuccessResponseDTO<Void>> findPassword(@Valid @RequestBody PWFindRequestDTO findPWRequestDTO,
                                                                              HttpSession session) {
        log.debug("Current Position: Controller:: 패스워드 찾기");

        PWFindResponseDTO pwFindResponseDTO = userService.queryFindPW(findPWRequestDTO);

        // 세션에 값을 설정
        session.setAttribute(SessionAttr.USER_ID.getKey(), pwFindResponseDTO.getUserId());

        // 세션 유효 시간을 10분으로 설정
        session.setMaxInactiveInterval(10 * 60); // 5분

        return ResponseEntity
                .status(SuccessCode.IS_OK.getStatus())
                .body(SuccessResponseDTO.of(null));
    }

    /**
     * 패스워드 찾기 이후 변경 작업
     */
    @PostMapping("/find/password/callback")
    public ResponseEntity<SuccessResponseDTO<Void>> findPasswordCallBack(@Valid @RequestBody ResetPWRequestDTO resetPWRequestDTO, HttpSession session) {
        log.debug("Current Position: Controller:: 패스워드 찾기 이후 변경 작업");

        userService.resetPW(resetPWRequestDTO, session);

        return ResponseEntity
                .status(SuccessCode.IS_OK.getStatus())
                .body(SuccessResponseDTO.of(null));
    }

    private ResponseEntity<SuccessResponseDTO<LoginResponseDTO>> processLoginPostActions(LoginSuccessDTO loginSuccessDTO) {
        // JWT & Refresh Token
        ResponseCookie jwtCookie = cookieUtils.createAccessTokenCookie(
                loginSuccessDTO.sessionTokensDTO().accessToken());

        ResponseCookie refreshTokenCookie = cookieUtils.createRefreshTokenCookie(
                loginSuccessDTO.sessionTokensDTO().refreshToken());

        // Response
        LoginResponseDTO loginResponseDTO = loginSuccessDTO.loginResponseDTO();

        return ResponseEntity
                .status(SuccessCode.IS_OK.getStatus())
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .header(HEADER_CSRF, loginSuccessDTO.csrfToken())
                .body(SuccessResponseDTO.of(loginResponseDTO));
    }
}
