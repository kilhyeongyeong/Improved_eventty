package com.eventty.authservice.applicaiton.service.Facade;

import com.eventty.authservice.applicaiton.dto.CsrfTokenDTO;
import com.eventty.authservice.applicaiton.dto.LoginSuccessDTO;
import com.eventty.authservice.applicaiton.dto.SessionTokensDTO;
import com.eventty.authservice.domain.Enum.UserRole;
import com.eventty.authservice.presentation.dto.request.*;
import com.eventty.authservice.presentation.dto.response.AuthenticationDetailsResponseDTO;
import com.eventty.authservice.presentation.dto.response.EmailFindResponseDTO;
import com.eventty.authservice.presentation.dto.response.LoginResponseDTO;
import com.eventty.authservice.presentation.dto.response.PWFindResponseDTO;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface UserService {

    Long createUser(FullUserCreateRequestDTO fullUserCreateRequestDTO, UserRole userRole);
    void validateEmailNotDuplicated(String email);
    LoginSuccessDTO login(UserLoginRequestDTO userLoginRequestDTO);
    AuthenticationDetailsResponseDTO authenticateUser(UserAuthenticateRequestDTO userAuthenticateRequestDTO);
    Long deleteUser(SessionTokensDTO sessionTokensDTO, String csrfToken);
    Long logout(SessionTokensDTO sessionTokensDTO, String csrfToken);
    CsrfTokenDTO changePW(PWChangeRequestDTO pwChangeRequestDTO, SessionTokensDTO sessionTokensDTO, String csrfToken);
    List<EmailFindResponseDTO> queryFindEmail(EmailFindRequestDTO emailFindRequestDTO);
    PWFindResponseDTO queryFindPW(PWFindRequestDTO pwFindRequestDTO);
    void resetPW(ResetPWRequestDTO resetPWRequestDTO, HttpSession session);
    LoginSuccessDTO oauthLogin(OAuthLoginRequestDTO oAuthLoginRequestDTO, String socialName);
}
