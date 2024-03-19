//package com.eventty.authservice.application.service.utils.token;
//
//
//import com.eventty.authservice.applicaiton.dto.TokensDTO;
//import com.eventty.authservice.applicaiton.service.utils.token.AccessTokenProvider;
//import com.eventty.authservice.applicaiton.service.utils.token.RefreshTokenProvider;
//import com.eventty.authservice.applicaiton.service.utils.token.TokenProperties;
//import com.eventty.authservice.applicaiton.service.utils.token.TokenProvider;
//import com.eventty.authservice.domain.Enum.UserRole;
//import com.eventty.authservice.domain.entity.AuthUserEntity;
//import com.eventty.authservice.domain.entity.AuthorityEntity;
//import com.eventty.authservice.domain.entity.RefreshTokenEntity;
//import com.eventty.authservice.domain.exception.InValidRefreshTokenException;
//import io.jsonwebtoken.JwtException;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//@EnableConfigurationProperties(TokenProperties.class)
//@ExtendWith(MockitoExtension.class)
//public class TokenProviderTest {
//
//    @InjectMocks
//    private TokenProvider tokenProvider;
//    @Mock
//    private TokenProperties tokenProperties;
//    @Mock
//    private AccessTokenProvider accessTokenProvider;
//    @Mock
//    private RefreshTokenProvider refreshTokenProvider;
//
//    @Test
//    @DisplayName("모든 토큰 가져오기 성공")
//    public void getAllToken_SUCCESS() {
//        // Given
//        Long id = 1L;
//        String email = "example1@mm.mm";
//        UserRole userRole = UserRole.USER;
//        AuthUserEntity authUserEntity = createAuthUserEntity(id, email, userRole);
//        Optional<RefreshTokenEntity> refreshTokenEntity = Optional.of(new RefreshTokenEntity());
//        String accessToken = "accessToken";
//        String refreshToken = "RefreshToken";
//
//        // When
//        when(accessTokenProvider.generateToken(eq(authUserEntity), any(Date.class), any(Date.class))).thenReturn(accessToken);
//        when(refreshTokenProvider.generateToken(eq(authUserEntity), any(Date.class), any(Date.class))).thenReturn(refreshToken);
//        when(refreshTokenProvider.saveOrUpdateRefreshToken(refreshToken, authUserEntity.getId())).thenReturn(refreshTokenEntity.get());
//        TokensDTO tokensDTO = tokenProvider.getAllToken(authUserEntity);
//
//        // Then
//        assertEquals(tokensDTO.getAccessToken(), accessToken);
//        assertEquals(tokensDTO.getRefreshToken(), refreshToken);
//    }
//
//    @Test
//    @DisplayName("모든 토큰 가져오기 실패 - AccessToken 가져오기 실패")
//    public void getAllToken_JWTException() {
//        // Given
//        Long id = 1L;
//        String email = "example1@mm.mm";
//        UserRole userRole = UserRole.USER;
//        AuthUserEntity authUserEntity = createAuthUserEntity(id, email, userRole);
//
//        // When
//        doThrow(new JwtException("")).when(accessTokenProvider).generateToken(eq(authUserEntity),  any(Date.class), any(Date.class));
//
//        // Then
//        assertThrows(JwtException.class, () -> tokenProvider.getAllToken(authUserEntity));
//    }
//
//    @Test
//    @DisplayName("리프레시 토큰 검증 성공")
//    public void refreshTokenValidationCheck_SUCCESS() {
//        // Given
//        Long userId = 1L;
//        String refreshToken = "refreshToken";
//        GetNewTokensRequestDTO getNewTokensRequestDTO = createGetNewTokensRequestDTO(userId, refreshToken);
//
//        // Given
//        when(refreshTokenProvider.findByRefreshToken(getNewTokensRequestDTO.getUserId())).thenReturn(refreshToken);
//        tokenProvider.refreshTokenValidationCheck(getNewTokensRequestDTO);
//
//        assertEquals(getNewTokensRequestDTO.getRefreshToken(), refreshTokenProvider.findByRefreshToken(getNewTokensRequestDTO.getUserId()));
//    }
//
//    @Test
//    @DisplayName("리프레시 토큰 검증 실패 - DB에 저장되어 있는 리프레시 토큰과 받아온 리프레시 토큰이 다른 경우")
//    public void refreshTokenValidationCheck_INVALID_REFRESH_TOKEN() {
//        // Given
//        Long userId = 1L;
//        String refreshToken = "refreshToken";
//        GetNewTokensRequestDTO getNewTokensRequestDTO = createGetNewTokensRequestDTO(userId, refreshToken);
//
//        // Given
//        when(refreshTokenProvider.findByRefreshToken(getNewTokensRequestDTO.getUserId())).thenReturn("Not Equal");
//
//        // Then
//        assertThrows(InValidRefreshTokenException.class, () -> tokenProvider.refreshTokenValidationCheck(getNewTokensRequestDTO));
//    }
//
//    private GetNewTokensRequestDTO createGetNewTokensRequestDTO(Long userId, String refreshToken) {
//        // Given
//        return GetNewTokensRequestDTO.builder()
//                .userId(userId)
//                .refreshToken(refreshToken)
//                .build();
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
//}
