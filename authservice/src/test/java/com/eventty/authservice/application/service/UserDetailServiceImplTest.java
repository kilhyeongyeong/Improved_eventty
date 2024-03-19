//package com.eventty.authservice.application.service;
//
//import com.eventty.authservice.domain.exception.AccessDeletedUserException;
//import com.eventty.authservice.domain.exception.UserNotFoundException;
//import jakarta.persistence.EntityManager;
//import org.junit.jupiter.api.extension.ExtendWith;
//
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import com.eventty.authservice.presentation.dto.request.FullUserCreateRequestDTO;
//import com.eventty.authservice.applicaiton.service.subservices.UserDetailServiceImpl;
//import com.eventty.authservice.domain.entity.AuthUserEntity;
//import com.eventty.authservice.domain.repository.AuthUserRepository;
//import com.eventty.authservice.domain.Enum.UserRole;
//import com.eventty.authservice.domain.entity.AuthorityEntity;
//import com.eventty.authservice.domain.exception.DuplicateEmailException;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//
//@ExtendWith(MockitoExtension.class)
//public class UserDetailServiceImplTest {
//
//    @InjectMocks
//    private UserDetailServiceImpl userDetailService;
//
//    @Mock
//    private AuthUserRepository userRepository;
//
//    @Mock
//    private EntityManager em;
//
//    // 패스워드 검증에는 인코더가 필요한 상황이므로 TestConfig에서 @Bean으로 등록 후 @Mock으로 등록
//    @Mock
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Test
//    @DisplayName("회원 삭제 성공")
//    public void delete_SUCCESS() {
//        // Given
//        Long userId = 1L;
//        AuthUserEntity authUserEntity = createAuthUserEntity(userId);
//
//        // When
//        userDetailService.delete(authUserEntity);
//
//        // Then
//        assertTrue(authUserEntity.isDelete());
//    }
//
//    @Test
//    @DisplayName("회원 찾기 성공 - Email")
//    public void findAuthUser_BY_EMAIL_SUCCESS() {
//        // Given
//        String email = "example1@mm.mm";
//        AuthUserEntity authUserEntity = createAuthUserEntity(email);
//
//        // When
//        when(userRepository.findByEmail(email)).thenReturn(Optional.ofNullable(authUserEntity));
//
//        // Then
//        assertEquals(userDetailService.findAuthUser(email), authUserEntity);
//    }
//
//    @Test
//    @DisplayName("회원 찾기 성공 - UserId")
//    public void findAuthUser_BY_USER_ID_SUCCESS() {
//        // Given
//        Long id = 1L;
//        AuthUserEntity authUserEntity = createAuthUserEntity(id);
//
//        // When
//        when(userRepository.findById(id)).thenReturn(Optional.of(authUserEntity));
//
//        // Then
//        assertEquals(userDetailService.findAuthUser(id), authUserEntity);
//    }
//
//    @Test
//    @DisplayName("회원 찾기 실패 - USER_NOT_FOUND")
//    public void findAuthUser_USER_NOT_FOUND() {
//        // Given
//        Long id = 1L;
//        String email = "example1@mm.mm";
//
//        // When
//        doThrow(new UserNotFoundException(id)).when(userRepository).findById(id);
//        doThrow(new UserNotFoundException(email)).when(userRepository).findByEmail(email);
//
//        // Then
//        assertThrows(UserNotFoundException.class, () -> userDetailService.findAuthUser(id));
//        assertThrows(UserNotFoundException.class, () -> userDetailService.findAuthUser(email));
//    }
//
//    @Test
//    @DisplayName("회원 생성 성공")
//    public void create_SUCCESS() {
//        // Given
//        Long userId = 1L;
//        AuthUserEntity authUserEntity = createAuthUserEntity(userId);
//        UserRole userRole = UserRole.USER;
//
//        // When
//        when(userRepository.findByEmail(authUserEntity.getEmail())).thenReturn(Optional.empty());
//        doNothing().when(em).persist(authUserEntity);
//        doNothing().when(em).persist(any(AuthorityEntity.class));
//
//        // Then
//        assertEquals(userDetailService.create(authUserEntity, userRole), userId);
//    }
//
//    @Test
//    @DisplayName("회원 생성 실패 - DUPLICATED_EMAIL")
//    public void create_DUPLICATED_EMAIL() {
//        // Given
//        Long userId = 1L;
//        AuthUserEntity authUserEntity = createAuthUserEntity(userId);
//        UserRole userRole = UserRole.USER;
//
//        // When
//        when(userRepository.findByEmail(authUserEntity.getEmail())).thenReturn(Optional.of(authUserEntity));
//
//        // Then
//        assertThrows(DuplicateEmailException.class, () -> userDetailService.create(authUserEntity, userRole));
//    }
//
//    @Test
//    @DisplayName("삭제된 회원 조회 - ACCESS_DELETED_USER")
//    public void validationUser() {
//        // Given
//        AuthUserEntity deletedAuthUserEntity = deletedAuthUserEntity();
//
//        // When && Then
//        assertThrows(AccessDeletedUserException.class, () -> userDetailService.validationUser(deletedAuthUserEntity));
//    }
//
//    private static AuthUserEntity deletedAuthUserEntity() {
//        return AuthUserEntity.builder()
//                .id(1L)
//                .email("example1@mm.mm")
//                .password("123123")
//                .isDelete(true)
//                .deleteDate(LocalDateTime.now())
//                .build();
//    }
//
//    private static AuthUserEntity createAuthUserEntity(String email) {
//        return AuthUserEntity.builder()
//                .id(1L)
//                .email(email)
//                .password("123123")
//                .build();
//    }
//
//    private static AuthUserEntity createAuthUserEntity(Long id) {
//        return AuthUserEntity.builder()
//                .id(id)
//                .email(createEmail(id))
//                .password("123123")
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
//
