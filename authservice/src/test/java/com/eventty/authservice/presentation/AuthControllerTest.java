//package com.eventty.authservice.presentation;
//
//import com.eventty.authservice.applicaiton.dto.LoginSuccessDTO;
//import com.eventty.authservice.applicaiton.dto.TokensDTO;
//import com.eventty.authservice.applicaiton.service.Facade.UserService;
//import com.eventty.authservice.domain.exception.InValidRefreshTokenException;
//import com.eventty.authservice.domain.exception.UserNotFoundException;
//import com.eventty.authservice.global.Enum.ErrorCode;
//import com.eventty.authservice.global.Enum.SuccessCode;
//import com.eventty.authservice.global.utils.DataErrorLogger;
//import com.eventty.authservice.infrastructure.config.WebConfig;
//import com.eventty.authservice.infrastructure.interceptor.AuthenticationInterceptor;
//import com.eventty.authservice.infrastructure.resolver.AuthenticationResolver;
//import com.eventty.authservice.infrastructure.resolver.LoginUser;
//import com.eventty.authservice.presentation.dto.request.UserLoginRequestDTO;
//import com.eventty.authservice.presentation.dto.response.LoginResponseDTO;
//import com.eventty.authservice.presentation.dto.response.NewTokensResponseDTO;
//import org.junit.jupiter.api.BeforeEach;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.mock.mockito.MockBeans;
//import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import com.eventty.authservice.presentation.dto.request.FullUserCreateRequestDTO;
//import com.eventty.authservice.infrastructure.config.BasicSecurityConfig;
//import com.eventty.authservice.domain.exception.DuplicateEmailException;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.eventty.authservice.domain.Enum.UserRole;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.springframework.security.test.
//        web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.mockito.Mockito.*;
//
///* 오류 정리
//1. h2.H2ConsoleProperties' available Error : BasicSecurityConfig에서 H2Console 설정 부분 주석 처리
//2. doThrow(DuplicateEmailException.class) 에서 에러 발생: 이와 같은 형태로 작성하는 것이 아닌 현재 커스텀 마이징 예외의 경우 Enum ErrorCode객체의 경우 생성자가 필요로 함.
//=>  doThrow(new DuplicateEmailException())
//3. csrf()를 사용하려면 의존성 주입 + imoprt static 작업 필요*/
//
//
//@WebMvcTest(AuthController.class)
//@MockBean(JpaMetamodelMappingContext.class)
//@MockBeans(value = {
//        @MockBean(WebConfig.class),
//        @MockBean(BasicSecurityConfig.class),
//        @MockBean(DataErrorLogger.class),
//        @MockBean(AuthenticationInterceptor.class),
//        @MockBean(AuthenticationConverter.class),
//        @MockBean(AuthenticationResolver.class)
//})
//public class AuthControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserService userService;
//
//    @Autowired
//    private WebApplicationContext context;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private AuthenticationConverter authenticationConverter;
//
//    @BeforeEach
//    public void setMockMvc() {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
//    }
//
//    @Test
//    @DisplayName("[POST] 회원 가입 테스트 성공_User")
//    public void createUserTest_SUCCESS() throws Exception {
//        // given
//        Long id = 1L;
//        String email = createEmail(id);
//        FullUserCreateRequestDTO fullUserCreateRequestDTO = createFullUserCreateRequestDTO(email);
//        UserRole userRole = UserRole.USER;
//
//        final String requestBody = objectMapper.writeValueAsString(fullUserCreateRequestDTO);
//
//        // When & Then
//        mockMvc.perform(post("/me/" + userRole)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody)
//                        .with(csrf()))
//                .andExpect(status().isCreated())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.success").value(true));
//    }
//
//    @Test
//    @DisplayName("[POST] 회원 가입 테스트 실패_USER - DuplicateEmailException")
//    public void createUserTest_INVALID_INPUT_VALUE() throws Exception {
//        // given
//        Long id = 1L;
//        String email = createEmail(id);
//        FullUserCreateRequestDTO fullUserCreateRequestDTO = createFullUserCreateRequestDTO(email);
//        UserRole userRole = UserRole.USER;
//        final String requestBody = objectMapper.writeValueAsString(fullUserCreateRequestDTO);
//
//        // When
//        doThrow(new DuplicateEmailException(email)).when(userService).createUser(any(FullUserCreateRequestDTO.class), any(UserRole.class));
//
//        // When & Then
//        mockMvc.perform(post("/me/" + userRole)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody)
//                        .with(csrf()))
//                .andExpect(status().is(ErrorCode.DUPLICATE_EMAIL.getStatus()))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.success").value(false)) // 실패 시 success 값이 false로 예상
//                .andExpect(jsonPath("$.errorResponseDTO.code").value(ErrorCode.DUPLICATE_EMAIL.getCode()));
//    }
//
//    @Test
//    @DisplayName("이메일 검증 - DUPLICATED_EMAIL")
//    public void isEmailDuplicateTest_DUPLICATED_EMAIL() throws Exception {
//        // Given
//        Long id = 1L;
//        String email = createEmail(id);
//
//        // Given: UserService의 isEmailDuplicate 메서드가 DuplicateEmailException을 던질 것을 모킹
//        doThrow(new DuplicateEmailException(email)).when(userService).validateEmailNotDuplicated(email);
//
//        // When  & Then
//        mockMvc.perform(post("/email")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"email\":\"" + email + "\"}")
//                        .with(csrf()))
//                .andExpect(status().is(ErrorCode.DUPLICATE_EMAIL.getStatus())) // 실패 시 BadRequest 상태 코드 예상
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.success").value(false)) // 실패 시 success 값이 false로 예상
//                .andExpect(jsonPath("$.errorResponseDTO.code").value(ErrorCode.DUPLICATE_EMAIL.getCode()));
//        // Verify that the isEmailDuplicate method was called with the provided email
//        verify(userService, times(1)).validateEmailNotDuplicated(email);
//    }
//
//    @Test
//    @DisplayName("로그인 성공")
//    public void login_SUCCESS() throws Exception {
//        // Given
//        UserLoginRequestDTO userLoginRequestDTO = createUserLoginRequestDTO();
//        LoginSuccessDTO loginSuccessDTO = createLoginSuccessDTO();
//        final String requestBody = objectMapper.writeValueAsString(userLoginRequestDTO);
//
//        // When
//        when(userService.login(any(UserLoginRequestDTO.class))).thenReturn(loginSuccessDTO);
//
//        // When & Then
//        mockMvc.perform(post("/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody)
//                        .with(csrf()))
//                .andExpect(status().is(SuccessCode.IS_OK.getStatus()))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(header().exists(HttpHeaders.SET_COOKIE))
//                .andExpect(jsonPath("$.success").value(true))
//                .andExpect(jsonPath("$.successResponseDTO").hasJsonPath());
//    }
//
//    @Test
//    @DisplayName("로그인 실패 - USER_NOT_FOUND")
//    public void login_USER_NOT_FOUND() throws Exception {
//        // Given
//        UserLoginRequestDTO userLoginRequestDTO = createUserLoginRequestDTO();
//        final String requestBody = objectMapper.writeValueAsString(userLoginRequestDTO);
//
//        // when
//        doThrow(new UserNotFoundException(userLoginRequestDTO.getEmail())).when(userService).login(any(UserLoginRequestDTO.class));
//
//        // When & Then
//        mockMvc.perform(post("/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody)
//                        .with(csrf()))
//                .andExpect(status().is(ErrorCode.USER_NOT_FOUND.getStatus()))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.success").value(false))
//                .andExpect(jsonPath("$.errorResponseDTO").hasJsonPath());
//    }
//
//    @Test
//    @DisplayName("회원 삭제 성공")
//    public void delete_SUCCESS() throws Exception {
//        // Given
//        LoginUser loginUser = LoginUser.builder()
//                .userId(1L)
//                .authorities(new ArrayList<>())
//                .build();
//
//        final String requestBody = objectMapper.writeValueAsString(loginUser);
//
//        // When
//        when(userService.deleteUser(loginUser.getUserId())).thenReturn(loginUser.getUserId());
//
//        // When & Then
//        mockMvc.perform(MockMvcRequestBuilders.delete("/me")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody)
//                        .with(csrf()))
//                .andExpect(status().is(SuccessCode.IS_OK.getStatus()))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.success").value(true));
//    }
//
//    @Test
//    @DisplayName("새로운 토큰 발급 성공")
//    public void getNewTokens_SUCCESS() throws Exception {
//        // Given
//        GetNewTokensRequestDTO getNewTokensRequestDTO = createGetNewTokensRequestDTO();
//        NewTokensResponseDTO newTokensResponseDTO = createNewTokensResponseDTO();
//
//        final String requestBody = objectMapper.writeValueAsString(getNewTokensRequestDTO);
//
//        // When
//        when(userService.getNewTokens(getNewTokensRequestDTO)).thenReturn(newTokensResponseDTO);
//
//        // When & Then
//        mockMvc.perform(post("/api/newtokens")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody)
//                        .with(csrf()))
//                .andExpect(status().is(SuccessCode.IS_OK.getStatus()))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.success").value(true))
//                .andExpect(jsonPath("$.successResponseDTO").hasJsonPath());
//    }
//
//    @Test
//    @DisplayName("새로운 토큰 발급 실패 - INVALID_REFRESH_TOKEN")
//    public void getNewTokens_INVALID_REFRESH_TOKEN() throws Exception{
//        // Given
//        GetNewTokensRequestDTO getNewTokensRequestDTO = createGetNewTokensRequestDTO();
//
//        final String requestBody = objectMapper.writeValueAsString(getNewTokensRequestDTO);
//
//        // When
//        doThrow(new InValidRefreshTokenException(getNewTokensRequestDTO))
//                .when(userService).getNewTokens(any(GetNewTokensRequestDTO.class));
//
//        // When & Then
//        mockMvc.perform(post("/api/newtokens")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody)
//                        .with(csrf()))
//                .andExpect(status().is(ErrorCode.INVALID_REFRESH_TOKEN.getStatus()))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.success").value(false))
//                .andExpect(jsonPath("$.errorResponseDTO").hasJsonPath());
//    }
//
//    private static GetNewTokensRequestDTO createGetNewTokensRequestDTO() {
//        return GetNewTokensRequestDTO.builder()
//                .userId(1L)
//                .refreshToken("Old RefreshToken")
//                .build();
//    }
//
//    private static NewTokensResponseDTO createNewTokensResponseDTO() {
//        return NewTokensResponseDTO.builder()
//                .accessToken("New AccessToken")
//                .refreshToken("New RefreshToken")
//                .build();
//    }
//
//
//    private static UserLoginRequestDTO createUserLoginRequestDTO() {
//        return UserLoginRequestDTO.builder()
//                .email("example1@mm.mm")
//                .password("123123")
//                .build();
//    }
//
//
//    private static LoginSuccessDTO createLoginSuccessDTO() {
//        List<String> authoritiesNameList = new ArrayList<>();
//        authoritiesNameList.add("ROLE_USER");
//        TokensDTO tokensDTO = TokensDTO.builder()
//                .accessToken("accessToken")
//                .refreshToken("refreshToken")
//                .build();
//        LoginResponseDTO loginResponseDTO = LoginResponseDTO.builder()
//                .email("example1@mm.mm")
//                .authoritiesNameList(authoritiesNameList)
//                .build();
//
//        return LoginSuccessDTO.builder()
//                .tokensDTO(tokensDTO)
//                .loginResponseDTO(loginResponseDTO)
//                .build();
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
