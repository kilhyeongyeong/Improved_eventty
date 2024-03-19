package com.eventty.authservice.applicaiton.service.subservices;

import com.eventty.authservice.applicaiton.dto.AuthenticationResultDTO;
import com.eventty.authservice.applicaiton.dto.CsrfTokenDTO;
import com.eventty.authservice.applicaiton.dto.TokenParsingDTO;
import com.eventty.authservice.applicaiton.dto.SessionTokensDTO;
import com.eventty.authservice.applicaiton.service.utils.CustomConverter;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.presentation.dto.request.UserLoginRequestDTO;
import jakarta.servlet.http.HttpSession;

public interface AuthService {

    AuthenticationResultDTO authenticate(SessionTokensDTO sessionTokensDTO, String csrfToken, CustomConverter converter, UserDetailService userDetailService);
    boolean credentialMatch(UserLoginRequestDTO userLoginRequestDTO, AuthUserEntity AuthUserEntity);

    SessionTokensDTO getToken(AuthUserEntity AuthUserEntity);
    TokenParsingDTO getTokenParsingDTO(SessionTokensDTO sessionTokensDTO);
    void csrfTokenValidationCheck(CsrfTokenDTO csrfTokenDTO);
    String getUpdateCsrfToken(Long userId);
    String getNewCsrfToken(Long userId);
    boolean checkCsrfToken(Long userId);
    void deleteAllToken(Long userId);
    boolean emailMatch (String email, AuthUserEntity AuthUserEntity);
    String encryptePassword(String rawPassword);
    Long getUserIdInSession(HttpSession sessison);
}
