package com.eventty.authservice.applicaiton.service.subservices;

import com.eventty.authservice.applicaiton.dto.AuthenticationResultDTO;
import com.eventty.authservice.applicaiton.dto.CsrfTokenDTO;
import com.eventty.authservice.applicaiton.dto.TokenParsingDTO;
import com.eventty.authservice.applicaiton.dto.SessionTokensDTO;
import com.eventty.authservice.applicaiton.service.utils.CustomConverter;
import com.eventty.authservice.applicaiton.service.utils.CustomPasswordEncoder;
import com.eventty.authservice.applicaiton.service.utils.token.TokenProvider;
import com.eventty.authservice.domain.Enum.SessionAttr;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.domain.exception.InvalidCsrfTokenException;
import com.eventty.authservice.domain.exception.InvalidPasswordException;
import com.eventty.authservice.domain.exception.SessionIsExpiredResetPasswordException;
import com.eventty.authservice.presentation.dto.request.UserLoginRequestDTO;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final TokenProvider tokenProvider;
    private final CustomPasswordEncoder customPasswordEncoder;

    // 전체 검증(JWT, CSRF)
    @Override
    public AuthenticationResultDTO authenticate(SessionTokensDTO sessionTokensDTO, String csrfToken, CustomConverter converter, UserDetailService userDetailService) {

        // 1차 검증을 통해 userId와 Token Update 필요한지 정보 가져오기
        TokenParsingDTO tokenParsingDTO = getTokenParsingDTO(sessionTokensDTO);
        Long userId= tokenParsingDTO.userId();

        // 2차 검증 (삭제되어 있는 User인지 확인)
        AuthUserEntity AuthUserEntity = userDetailService.findAuthUser(userId);

        // 3차 검증 (CSRF 검증) => 성공시 재발급 및 저장
        CsrfTokenDTO csrfTokenDTO = converter.convertCsrfTokenDTO(userId, csrfToken);
        csrfTokenValidationCheck(csrfTokenDTO);

        return new AuthenticationResultDTO(AuthUserEntity, tokenParsingDTO.needsUpdate());
    }

    // CSRF 유효한지 확인
    @Override
    public void csrfTokenValidationCheck(CsrfTokenDTO csrfTokenDTO) {
        String existedCsrfToken = tokenProvider.getCsrfToken(csrfTokenDTO.userId());

        if (!csrfTokenDTO.value().equals(existedCsrfToken))
            throw new InvalidCsrfTokenException(csrfTokenDTO);
    }

    // 로그인과 검증 로직간에 차이가 있는데, 같은 함수를 사용하면 제한이 걸리는 상황이라서 저장과 업데이트 구분
    @Override
    public String getNewCsrfToken(Long userId) {
        return tokenProvider.saveCsrfToken(userId);
    }

    @Override
    public boolean checkCsrfToken(Long userId) {
        return tokenProvider.checkCsrfTokenInDB(userId);
    }

    @Override
    public String getUpdateCsrfToken(Long userId) {
        return tokenProvider.updateCsrfToken(userId);
    }

    // 토큰 파싱하여 필요한 정보 DTO로 반환
    @Override
    public TokenParsingDTO getTokenParsingDTO(SessionTokensDTO sessionTokensDTO) {
        return tokenProvider.parsingToken(sessionTokensDTO);
    }

    // 비밀번호 매칭
    @Override
    public boolean credentialMatch(UserLoginRequestDTO userLoginRequestDTO, AuthUserEntity AuthUserEntity) {
        if (!customPasswordEncoder.match(userLoginRequestDTO.getPassword(), AuthUserEntity.getPassword())) {
            throw new InvalidPasswordException(userLoginRequestDTO);
        }

        return true;
    }

    // 검증 로직 X
    @Override
    public SessionTokensDTO getToken(AuthUserEntity AuthUserEntity) {
        // 2시간 동안 유효한 액세스 토큰 생성 및 2일 동안 유효한 리프레시 토큰 생성 
        return tokenProvider.getAllToken(AuthUserEntity);
    }

    // 모든 토큰 삭제 -> 만약 없다면 예외 발생시키지 말고 그냥 넘어가기
    @Override
    public void deleteAllToken(Long userId) {

        // Refresh Token 삭제
        tokenProvider.deleteRefreshToken(userId);

        // CSRF Token 삭제
        tokenProvider.deleteCsrfToken(userId);
    }

    @Override
    public boolean emailMatch(String email, AuthUserEntity AuthUserEntity) {
        return email.equals(AuthUserEntity.getEmail());
    }

    @Override
    public String encryptePassword(String rawPassword) {
        return customPasswordEncoder.encodePassword(rawPassword);
    }

    @Override
    public Long getUserIdInSession(HttpSession session) {
        Object userIdInSession = session.getAttribute(SessionAttr.USER_ID.getKey());

        if (userIdInSession == null)
            throw new SessionIsExpiredResetPasswordException();

        // 세션 파기 시키기
        session.invalidate();

        return Long.parseLong(userIdInSession.toString());
    }
}
