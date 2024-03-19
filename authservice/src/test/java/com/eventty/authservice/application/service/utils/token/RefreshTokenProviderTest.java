//package com.eventty.authservice.application.service.utils.token;
//
//
//import com.eventty.authservice.applicaiton.service.utils.token.RefreshTokenProvider;
//import com.eventty.authservice.applicaiton.service.utils.token.TokenProperties;
//import com.eventty.authservice.domain.Enum.UserRole;
//import com.eventty.authservice.domain.entity.AuthUserEntity;
//import com.eventty.authservice.domain.entity.AuthorityEntity;
//import com.eventty.authservice.domain.entity.RefreshTokenEntity;
//import com.eventty.authservice.domain.repository.RefreshTokenRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//
//import java.time.Duration;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@EnableConfigurationProperties(TokenProperties.class)
//@ExtendWith(MockitoExtension.class)
//public class RefreshTokenProviderTest {
//    @InjectMocks
//    private RefreshTokenProvider refreshTokenProvider;
//
//    @Mock
//    private TokenProperties tokenProperties;
//
//    @Mock
//    private RefreshTokenRepository refreshTokenRepository;
//
//    @Test
//    @DisplayName("Refresh Token 토큰 생성 성공")
//    void generateToken_Success() {
//        // Given
//        Long id = 1L;
//        String email = "example1@mm.mm";
//        UserRole role = UserRole.USER;
//        AuthUserEntity authUserEntity = createAuthUserEntity(id, email, role);
//        Date now = new Date();
//        Date expired = createExpiry(now, Duration.ofDays(tokenProperties.getRefreshExpiredTime()));
//
//        // When
//        when(tokenProperties.getSecretKey()).thenReturn("secret");
//        when(tokenProperties.getIssuer()).thenReturn("issuer");
//        refreshTokenProvider.generateToken(authUserEntity, now , expired);
//
//        // Then
//        assertNotNull(refreshTokenProvider.generateToken(authUserEntity, now, expired));
//    }
//
//    @Test
//    @DisplayName("Refresh Token 업데이트: 존재하지 않는 경우")
//    public void saveOrUpdateRefreshToken_SAVE_SUCCESS() {
//        // Given
//        Long userId = 1L;
//        String newName = "refresh";
//
//        // When
//        when(refreshTokenRepository.findByUserId(userId)).thenReturn(Optional.empty());
//        RefreshTokenEntity newToken = RefreshTokenEntity.builder().userId(userId).name(newName).build();
//        when(refreshTokenRepository.save(any(RefreshTokenEntity.class))).thenReturn(newToken);
//        RefreshTokenEntity result = refreshTokenProvider.saveOrUpdateRefreshToken(newName, userId);
//
//        // Then
//        assertEquals(newName, result.getName());
//        verify(refreshTokenRepository, times(1)).save(any(RefreshTokenEntity.class));
//    }
//
//    @Test
//    @DisplayName("Refresh Token 업데이트: 이미 존재하는 경우")
//    public void testSaveNewRefreshToken_ALREADY_SAVED_TOKEN_UPDATE() {
//        // Given
//        Long userId = 1L;
//        String newName = "newName";
//        RefreshTokenEntity existingToken = new RefreshTokenEntity();
//        when(refreshTokenRepository.findByUserId(userId)).thenReturn(Optional.of(existingToken));
//        when(refreshTokenRepository.save(existingToken)).thenReturn(existingToken);
//
//        // When
//        when(refreshTokenRepository.findByUserId(userId)).thenReturn(Optional.of(existingToken));
//        RefreshTokenEntity result = refreshTokenProvider.saveOrUpdateRefreshToken(newName, userId);
//
//        // Then
//        assertEquals(newName, result.getName());
//        verify(refreshTokenRepository, times(1)).save(any(RefreshTokenEntity.class));
//    }
//
//    private Optional<RefreshTokenEntity> createRefreshTokenEntity(Long userId, String refreshToken) {
//        RefreshTokenEntity exsitedRefreshTokenEntity = RefreshTokenEntity.builder()
//                .userId(userId)
//                .name(refreshToken)
//                .build();
//        return Optional.of(exsitedRefreshTokenEntity);
//    }
//
//    private AuthUserEntity createAuthUserEntity(Long id, String email, UserRole role) {
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
//    private Date createExpiry(Date now, Duration expiredAt) {
//        return new Date(now.getTime() + expiredAt.toMillis());
//    }
//}
