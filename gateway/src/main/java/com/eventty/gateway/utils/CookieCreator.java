package com.eventty.gateway.utils;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieCreator {

    public static ResponseCookie createAccessTokenCookie(String token) {
        return ResponseCookie.from(TokenEnum.ACCESS_TOKEN.getName(), token)
                .httpOnly(true)
                .path("/")
                //.domain("eventty")
                .maxAge(2 * 60 * 60 + 30 * 60)  // 2시간 30분
                .build();
    }

    public static ResponseCookie createRefreshTokenCookie(String token) {
        return ResponseCookie.from(TokenEnum.REFRESH_TOKEN.getName(), token)
                .httpOnly(true)
                .path("/")
                //.domain("eventty")
                .maxAge(3 * 24 * 60 * 60) // 3일
                .build();
    }


}
