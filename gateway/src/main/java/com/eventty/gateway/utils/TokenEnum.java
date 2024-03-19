package com.eventty.gateway.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenEnum {

    ACCESS_TOKEN("accessToken"),
    REFRESH_TOKEN("refreshToken"),
    USERID("userId"),
    AUTHORIZATION("authorities");


    private final String name;
}
