//package com.eventty.authservice.application.service.utils.token;
//
//import com.eventty.authservice.applicaiton.service.utils.token.AccessTokenProvider;
//import com.eventty.authservice.applicaiton.service.utils.token.TokenProperties;
//import com.eventty.authservice.domain.Enum.UserRole;
//import com.eventty.authservice.domain.entity.AuthUserEntity;
//import com.eventty.authservice.domain.entity.AuthorityEntity;
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
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@EnableConfigurationProperties(TokenProperties.class)
//@ExtendWith(MockitoExtension.class)
//public class AccssTokenProviderTest {
//
//    @InjectMocks
//    private AccessTokenProvider accessTokenProvider;
//
//    @Mock
//    private TokenProperties tokenProperties;
//
//    @Test
//    @DisplayName("Access Token 토큰 생성 성공")
//    public void generateToken_Success() {
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
//        accessTokenProvider.generateToken(authUserEntity, now, expired);
//
//        // Then
//        assertNotNull(accessTokenProvider.generateToken(
//                authUserEntity, now, expired));
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
//
//
//}
