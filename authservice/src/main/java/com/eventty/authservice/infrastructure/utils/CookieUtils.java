package com.eventty.authservice.infrastructure.utils;

import com.eventty.authservice.applicaiton.dto.SessionTokensDTO;
import com.eventty.authservice.applicaiton.service.utils.token.TokenEnum;
import com.eventty.authservice.domain.exception.PermissionDeniedException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
public class CookieUtils {

    public ResponseCookie createAccessTokenCookie(String token) {
        return ResponseCookie.from(TokenEnum.ACCESS_TOKEN.getName(), token)
                .path("/")
                .httpOnly(true)
                .maxAge(2 * 60 * 60 + 30 * 60)
                .build();
    }

    public ResponseCookie createRefreshTokenCookie(String token) {
        return ResponseCookie.from(TokenEnum.REFRESH_TOKEN.getName(), token)
                .path("/")
                .httpOnly(true)
                .maxAge(3 * 24 * 60 * 60)
                .build();
    }

    // JWT 쿠키 무효화
    public ResponseCookie deleteAccessTokenCookie() {
        return ResponseCookie.from(TokenEnum.ACCESS_TOKEN.getName())
                .path("/")
                .httpOnly(true)
                .maxAge(0) // 쿠키의 유효 기간을 0으로 설정하여 즉시 만료시킵니다.
                .build();
    }

    // RefreshToken 쿠키 무효화
    public ResponseCookie deleteRefreshTokenCookie() {
        return ResponseCookie.from(TokenEnum.REFRESH_TOKEN.getName())
                .path("/")
                .httpOnly(true)
                .maxAge(0)
                .build();
    }

    // Session 토큰 꺼내오기
    public SessionTokensDTO getSessionTokens(HttpServletRequest request) {

        final String HEADER_CSRF = "X-Csrf-Token";

        Cookie[] cookies = request.getCookies();
        String accessToken = getJwt(cookies);
        String refreshToken = getRefreshToken(cookies);

        return new SessionTokensDTO(accessToken, refreshToken);
    }

    // CSRF 토큰 꺼내오기
    public String getCsrfToken(HttpServletRequest request) {
        final String HEADER_CSRF = "X-Csrf-Token";

        return request.getHeader(HEADER_CSRF);
    }


    /*
     * Tokens Method
     */

    private String getJwt(Cookie[] cookies) {
        Optional<Cookie> jwtCookie = Arrays.stream(cookies).filter(cookie ->
                        cookie.getName().equals(TokenEnum.ACCESS_TOKEN.getName())
                )
                .findFirst();
        return jwtCookie.orElseThrow(PermissionDeniedException::new).getValue();
    }

    private String getRefreshToken(Cookie[] cookies) {
        Optional<Cookie> refreshTokenCookie = Arrays.stream(cookies).filter(cookie ->
                        cookie.getName().equals(TokenEnum.REFRESH_TOKEN.getName()))
                .findFirst();

        return refreshTokenCookie.map(Cookie::getValue).orElse("");
    }
}
