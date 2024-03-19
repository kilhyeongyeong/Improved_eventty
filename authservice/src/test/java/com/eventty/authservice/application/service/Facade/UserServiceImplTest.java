//package com.eventty.authservice.application.service.Facade;
//
//import com.eventty.authservice.api.ApiClient;
//import com.eventty.authservice.api.exception.ApiException;
//import com.eventty.authservice.applicaiton.dto.LoginSuccessDTO;
//import com.eventty.authservice.applicaiton.dto.TokensDTO;
//import com.eventty.authservice.applicaiton.service.subservices.AuthServiceImpl;
//import com.eventty.authservice.applicaiton.service.subservices.UserDetailServiceImpl;
//import com.eventty.authservice.applicaiton.service.utils.CustomConverter;
//import com.eventty.authservice.applicaiton.service.utils.CustomPasswordEncoder;
//import com.eventty.authservice.domain.exception.AccessDeletedUserException;
//import com.eventty.authservice.domain.exception.InvalidPasswordException;
//import com.eventty.authservice.domain.exception.UserNotFoundException;
//import com.eventty.authservice.global.response.ResponseDTO;
//import com.eventty.authservice.presentation.dto.request.UserLoginRequestDTO;
//import com.eventty.authservice.presentation.dto.response.LoginResponseDTO;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.eventty.authservice.api.dto.request.UserCreateRequestDTO;
//import com.eventty.authservice.applicaiton.service.Facade.UserServiceImpl;
//import com.eventty.authservice.domain.Enum.UserRole;
//import com.eventty.authservice.domain.entity.AuthUserEntity;
//import com.eventty.authservice.domain.entity.AuthorityEntity;
//import com.eventty.authservice.domain.exception.DuplicateEmailException;
//import com.eventty.authservice.presentation.dto.request.FullUserCreateRequestDTO;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//public class UserServiceImplTest {
//
//    @InjectMocks
//    private UserServiceImpl userService;
//
//    @Mock
//    private UserDetailServiceImpl userDetailService;
//    @Mock
//    private AuthServiceImpl authService;
//    @Mock
//    private ApiClient apiClient;
//    @Mock
//    CustomConverter customConverter;
//    @Mock
//    CustomPasswordEncoder customPasswordEncoder;
//
//    @Test
//    @DisplayName("로그인 성공")
//    public void login_SUCCESS() {
//        // Given
//        Long userId = 1L;
//        String email = "example1@mm.mm";
//        String password = "123123";
//        UserRole role = UserRole.USER;
//        UserLoginRequestDTO userLoginRequestDTO = createUserLoginRequestDTO(email, password, customPasswordEncoder);
//        AuthUserEntity authUserEntity = createAuthUserEntity(userId, email, role);
//        LoginSuccessDTO loginSuccessDTO = LoginSuccessDTO.builder()
//                .loginResponseDTO(new LoginResponseDTO(email, new ArrayList<>()))
//                .tokensDTO(new TokensDTO("", ""))
//                .build();
//
//        when(userDetailService.findAuthUser(userLoginRequestDTO.getEmail())).thenReturn(authUserEntity);
//        doNothing().when(userDetailService).validationUser(authUserEntity);
//        when(authService.credentialMatch(userLoginRequestDTO, authUserEntity, customPasswordEncoder)).thenReturn(true);
//        when(customConverter.authUserEntityTologinSuccessDTO(authService, authUserEntity)).thenReturn(loginSuccessDTO);
//
//        // When & Then
//        assertEquals(userService.login(userLoginRequestDTO), loginSuccessDTO);
//
//        // Verify
//        verify(userDetailService, times(1)).findAuthUser(userLoginRequestDTO.getEmail());
//        verify(customConverter, times(1)).authUserEntityTologinSuccessDTO(authService, authUserEntity);
//    }
//
//    @Test
//    @DisplayName("로그인 실패 - 유저가 삭제되어 있는 경우")
//    public void login_ACCESS_DELETED_USER() {
//        // Given
//        Long userId = 1L;
//        String email = "example1@mm.mm";
//        String password = "123123";
//        UserRole role = UserRole.USER;
//        UserLoginRequestDTO userLoginRequestDTO = createUserLoginRequestDTO(email, password, customPasswordEncoder);
//        AuthUserEntity authUserEntity = createAuthUserEntity(userId, email, role);
//
//        when(userDetailService.findAuthUser(userLoginRequestDTO.getEmail())).thenReturn(authUserEntity);
//        doThrow(new AccessDeletedUserException(authUserEntity)).when(userDetailService).validationUser(authUserEntity);
//
//        // When & Then
//        assertThrows(AccessDeletedUserException.class, () -> userService.login(userLoginRequestDTO));
//
//        // Verify
//        verify(authService, never()).credentialMatch(userLoginRequestDTO, authUserEntity, customPasswordEncoder);
//        verify(customConverter, never()).authUserEntityTologinSuccessDTO(authService, authUserEntity);
//    }
//
//    @Test
//    @DisplayName("로그인 실패 - 비밀번호 매칭 실패")
//    public void login_INVALID_PASSWORD() {
//        // Given
//        Long userId = 1L;
//        String email = "example1@mm.mm";
//        String password = "123123";
//        UserRole role = UserRole.USER;
//        UserLoginRequestDTO userLoginRequestDTO = createUserLoginRequestDTO(email, password, customPasswordEncoder);
//        AuthUserEntity authUserEntity = createAuthUserEntity(userId, email, role);
//
//        when(userDetailService.findAuthUser(userLoginRequestDTO.getEmail())).thenReturn(authUserEntity);
//        doNothing().when(userDetailService).validationUser(authUserEntity);
//        doThrow(new InvalidPasswordException(userLoginRequestDTO)).when(authService).credentialMatch(userLoginRequestDTO, authUserEntity, customPasswordEncoder);
//
//        // When & Then
//        assertThrows(InvalidPasswordException.class, () -> userService.login(userLoginRequestDTO));
//
//        // Verify
//        verify(customConverter, never()).authUserEntityTologinSuccessDTO(authService, authUserEntity);
//    }
//
//
//    @Test
//    @DisplayName("회원가입 성공")
//    public void createUser_SUCESS_USER() {
//        // Given
//        Long userId = 1L;
//        String email = createEmail(userId);
//        FullUserCreateRequestDTO fullUserCreateRequestDTO = createFullUserCreateRequestDTO(email);
//        UserRole role = UserRole.USER;
//        AuthUserEntity authUserEntity = createAuthUserEntity(userId, email, role);
//        UserCreateRequestDTO userCreateRequestDTO = createUserCreateRequestDTO(fullUserCreateRequestDTO, userId);
//        ResponseEntity<ResponseDTO<Void>> responseEntity = createResponseEntity();
//
//        when(customConverter.userDTOToAuthEntityConvert(fullUserCreateRequestDTO, customPasswordEncoder)).thenReturn(authUserEntity);
//        when(userDetailService.create(authUserEntity, role)).thenReturn(userId);
//        when(customConverter.fullUserDTOToUserDTO(fullUserCreateRequestDTO, userId)).thenReturn(userCreateRequestDTO);
//        when(apiClient.createUserApi(userCreateRequestDTO)).thenReturn(responseEntity);
//
//        // When & Then
//        assertEquals(userService.createUser(fullUserCreateRequestDTO, role), userId);
//
//        // Verify
//        verify(customConverter, times(1)).userDTOToAuthEntityConvert(fullUserCreateRequestDTO, customPasswordEncoder);
//        verify(userDetailService, times(1)).create(authUserEntity, role);
//        verify(customConverter, times(1)).fullUserDTOToUserDTO(fullUserCreateRequestDTO, userId);
//    }
//
//    @Test
//    @DisplayName("회원 가입 실패 - 이메일 중복")
//    public void createUser_INVALID_INPUT_VALUE() {
//
//        // Given
//        Long userId = 1L;
//        String email = createEmail(userId);
//        FullUserCreateRequestDTO fullUserCreateRequestDTO = createFullUserCreateRequestDTO(email);
//        UserRole role = UserRole.USER;
//        AuthUserEntity authUserEntity = createAuthUserEntity(userId, email, role);
//
//        when(customConverter.userDTOToAuthEntityConvert(fullUserCreateRequestDTO, customPasswordEncoder)).thenReturn(authUserEntity);
//        doThrow(DuplicateEmailException.class).when(userDetailService).create(authUserEntity, role);
//
//        // when & Then
//        assertThrows(DuplicateEmailException.class, () -> userService.createUser(fullUserCreateRequestDTO, role));
//
//        // Verify
//        verify(customConverter, times(0)).fullUserDTOToUserDTO(fullUserCreateRequestDTO, userId);
//    }
//    @Test
//    @DisplayName("회원 가입 실패 - API 요청 실패")
//    public void createUser_API_EXCEPTION() {
//        // Given
//        Long userId = 1L;
//        String email = createEmail(userId);
//        FullUserCreateRequestDTO fullUserCreateRequestDTO = createFullUserCreateRequestDTO(email);
//        UserRole role = UserRole.USER;
//        AuthUserEntity authUserEntity = createAuthUserEntity(userId, email, role);
//        UserCreateRequestDTO userCreateRequestDTO = createUserCreateRequestDTO(fullUserCreateRequestDTO, userId);
//
//        when(customConverter.userDTOToAuthEntityConvert(fullUserCreateRequestDTO, customPasswordEncoder)).thenReturn(authUserEntity);
//        when(userDetailService.create(authUserEntity, role)).thenReturn(userId);
//        when(customConverter.fullUserDTOToUserDTO(fullUserCreateRequestDTO, userId)).thenReturn(userCreateRequestDTO);
//        doThrow(ApiException.class).when(apiClient).createUserApi(any(UserCreateRequestDTO.class));
//
//        // When & then
//        assertThrows(ApiException.class, () -> userService.createUser(fullUserCreateRequestDTO, role));
//    }
//
//    @Test
//    @DisplayName("유저 삭제 성공")
//    public void deleteUser_SUCCESS() {
//        // Given
//        Long userId = 1L;
//        String email = "example1@mm.mm";
//        UserRole role = UserRole.USER;
//
//        AuthUserEntity authUserEntity = createAuthUserEntity(userId, email, role);
//
//        when(userDetailService.findAuthUser(userId)).thenReturn(authUserEntity);
//        when(userDetailService.delete(authUserEntity)).thenReturn(1L);
//
//        // When & Then
//        assertEquals(userService.deleteUser(userId), 1L);
//
//        // Verify
//        verify(userDetailService, times(1)).findAuthUser(userId);
//        verify(userDetailService, times(1)).delete(authUserEntity);
//    }
//
//    @Test
//    @DisplayName("유저 삭제 실패 - 해당 ID로 User를 찾을 수 없는 경우")
//    public void deleteUser_USER_NOT_FOUND() {
//        // Given
//        Long userId = 1L;
//
//        doThrow(new UserNotFoundException(userId)).when(userDetailService).findAuthUser(userId);
//
//        // When & Then
//        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(userId));
//
//        // Verify
//        verify(userDetailService, never()).delete(any(AuthUserEntity.class));
//    }
//
//    private ResponseEntity<ResponseDTO<Void>> createResponseEntity() {
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(ResponseDTO.of(true));
//    }
//
//    private UserLoginRequestDTO createUserLoginRequestDTO(String email, String password, CustomPasswordEncoder customPasswordEncoder) {
//        return UserLoginRequestDTO.builder()
//                .email(email)
//                .password(customPasswordEncoder.encode(password))
//                .build();
//    }
//
//    private UserCreateRequestDTO createUserCreateRequestDTO(FullUserCreateRequestDTO fullUserCreateRequestDTO, Long id) {
//        return UserCreateRequestDTO.builder()
//                .userId(id)
//                .name(fullUserCreateRequestDTO.getName())
//                .address(fullUserCreateRequestDTO.getAddress())
//                .birth(fullUserCreateRequestDTO.getBirth())
//                .phone(fullUserCreateRequestDTO.getPhone())
//                .build();
//    }
//
//    private static AuthUserEntity createAuthUserEntity(Long id, String email, UserRole role) {
//        AuthUserEntity authUserEntity = AuthUserEntity.builder()
//                .id(id)
//                .email(email)
//                .password("123123")
//                .build();
//
//        AuthorityEntity authorityEntity = AuthorityEntity.builder()
//                .id(id)
//                .name(role.getRole())
//                .authUserEntity(authUserEntity)
//                .build();
//
//        List<AuthorityEntity> Authorities = new ArrayList<>();
//        Authorities.add(authorityEntity);
//
//        authUserEntity.setAuthorities(Authorities);
//
//        return authUserEntity;
//    }
//
//    private static String createEmail(Long id) {
//        return String.format("Example%d@mm.mm", id);
//    }
//
//    private static FullUserCreateRequestDTO createFullUserCreateRequestDTO(String email) {
//        return FullUserCreateRequestDTO.builder()
//                .email(email)
//                .password("123123")
//                .name("eventty0")
//                .address("서울시 강남")
//                .birth(LocalDate.now())
//                .phone("000-0000-0000")
//                .build();
//    }
//}
