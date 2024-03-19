package com.eventty.authservice.infrastructure.utils;

import com.eventty.authservice.domain.Enum.UserRole;
import org.springframework.core.convert.converter.Converter;

public class StringToUserRoleConverter implements Converter<String, UserRole> {
    @Override
    public UserRole convert(String source) {
        return UserRole.valueOf(source.toUpperCase());
    }
}
