package com.eventty.authservice.domain.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {

    HOST("HOST"),
    USER("USER"),
    MANAGER("MANAGER");

    private final String role;

    public String getRole() {
        return "ROLE_" + this.role;
    }
}
