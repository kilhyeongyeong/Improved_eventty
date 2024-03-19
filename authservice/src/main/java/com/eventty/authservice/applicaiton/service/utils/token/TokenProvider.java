package com.eventty.authservice.applicaiton.service.utils.token;

import com.eventty.authservice.applicaiton.dto.TokenParsingDTO;
import com.eventty.authservice.applicaiton.dto.SessionTokensDTO;
import com.eventty.authservice.applicaiton.service.utils.CustomConverter;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.domain.exception.CsrfTokenNotFoundException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
@AllArgsConstructor
public class TokenProvider {

    private final TokenProperties tokenProperties;

    private final AccessTokenProvider accessTokenProvider;

    private final RefreshTokenProvider refreshTokenProvider;

    private final CSRFTokenProvider csrfTokenProvider;
    private final CustomConverter customConverter;


    // 두개 이상 토큰 처리 메서드
    public SessionTokensDTO getAllToken(AuthUserEntity AuthUserEntity) {

        Date now = new Date();

        // Access Token의 만료 시간: 2시간
        String accessToken = accessTokenProvider.generateToken(AuthUserEntity, now, createExpiry(now, Duration.ofHours(tokenProperties.getAccessExpiredTime())));

        // Refresh Token의 만교 기간: 2일
        String refreshToken = refreshTokenProvider.generate(AuthUserEntity, now, createExpiry(now, Duration.ofDays(tokenProperties.getRefreshExpiredTime())));

        // Refresh Token 저장 혹은 업데이트
        refreshTokenProvider.saveOrUpdate(refreshToken, AuthUserEntity.getId());

        return new SessionTokensDTO(accessToken, refreshToken);
    }

    // Token Parsing해서 User Id와 needsUpdate 반환 (1차 검증)
    public TokenParsingDTO parsingToken(SessionTokensDTO SessionTokensDTO) {

        // JWT를 이용해서 JWT Cliams 가져오기
        Claims claims = getClaimsOrNullOnExpiration(SessionTokensDTO.accessToken());

        // 만약 만료 기간이 지났다면,
        if (claims == null) {

            // Refresh Token 이용해서 Claims update
            claims = getClaimsOrThrow(SessionTokensDTO.refreshToken());

            // User ID 가져오기
            Long userId = getUserId(claims);

            // Refresh Token Validation Check
            refreshTokenProvider.validationCheck(customConverter.convertToValidationRefreshTokenDTO(
                    userId, SessionTokensDTO
            ));

            // 새로 업데이트 된다는 정보 보내기
            return new TokenParsingDTO(userId, true);
        }
        return new TokenParsingDTO(getUserId(claims), false);
    }

    // 하나의 토큰 처리

    // Refresh Token
    public String deleteRefreshToken(Long userId) { return refreshTokenProvider.delete(userId); }

    // CSRF Token
    public String getCsrfToken(Long userId) {
        // User Id 이용해서 저장되어 있는 CSRF Token 가저오기
        return csrfTokenProvider.get(userId)
                .orElseThrow(() -> new CsrfTokenNotFoundException(userId))
                .getName();
    }

    // Csrf Check
    public boolean checkCsrfTokenInDB(Long userId) {
        return csrfTokenProvider.get(userId).isPresent();
    }

    public String updateCsrfToken(Long userId) {
        return csrfTokenProvider.update(userId);
    }

    public String saveCsrfToken(Long userId) {
        return csrfTokenProvider.save(userId);
    }

    public String deleteCsrfToken(Long userId) { return csrfTokenProvider.delete(userId); }

    // 토큰 Proivder에 두어서 직접 파싱
    // 만료 기간이 지난 경우에는 예외
    private Claims getClaimsOrNullOnExpiration(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(tokenProperties.getSecretKey())
                    .requireIssuer(tokenProperties.getIssuer())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return null;
        }
        return claims;
    }

    // 서명이 유효하지 않은 경우, 만료 기간 지난 경우, 파싱 실패 전부 예외 터트리기
    private Claims getClaimsOrThrow(String token) {
        return Jwts.parser()
                .setSigningKey(tokenProperties.getSecretKey())
                .requireIssuer(tokenProperties.getIssuer())
                .parseClaimsJws(token)
                .getBody();
    }

    private Long getUserId(Claims claims) {
        return Long.parseLong(claims.get(TokenEnum.USERID.getName()).toString());
    }

    private Date createExpiry(Date now, Duration expiredAt) {
        return new Date(now.getTime() + expiredAt.toMillis());
    }
}
