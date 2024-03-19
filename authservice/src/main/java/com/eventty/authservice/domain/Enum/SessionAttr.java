package com.eventty.authservice.domain.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public enum SessionAttr {

    USER_ID("USER_ID");

    private final String key;
}
