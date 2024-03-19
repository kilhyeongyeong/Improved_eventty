package com.eventty.authservice.applicaiton.service.utils.token;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenEnum {

    ACCESS_TOKEN("accessToken"),
    REFRESH_TOKEN("refreshToken"),
    USERID("userId");

    private final String name;
}
