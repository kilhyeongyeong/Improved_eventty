package com.eventty.authservice.domain.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;

import java.net.URI;

@Getter
@RequiredArgsConstructor
public enum OAuth {

    GOOGLE("google",
            URI.create("https://www.googleapis.com/oauth2/v2/userinfo"),
            URI.create("https://oauth2.googleapis.com/token"),
            HttpMethod.GET,
            HttpMethod.POST),
    NAVER("naver",
            URI.create("https://openapi.naver.com/v1/nid/me"),
            URI.create("https://nid.naver.com/oauth2.0/token"),
            HttpMethod.GET,
            HttpMethod.POST),
    KAKAO("kakao",
            URI.create("https://kapi.kakao.com/v2/user/me"),
            URI.create("https://kauth.kakao.com/oauth/token"),
            HttpMethod.GET,
            HttpMethod.POST);

    private final String socialName;
    private final URI userInfoUri;
    private final URI tokenUri;
    private final HttpMethod userInfoMethod;
    private final HttpMethod tokenMethod;
}
