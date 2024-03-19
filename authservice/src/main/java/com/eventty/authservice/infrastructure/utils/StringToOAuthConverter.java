package com.eventty.authservice.infrastructure.utils;

import com.eventty.authservice.domain.Enum.OAuth;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToOAuthConverter implements Converter<String, OAuth> {

    @Override
    public OAuth convert(String source) {
        for (OAuth oAuth : OAuth.values()) {
            if (oAuth.getSocialName().equalsIgnoreCase(source)) {
                return oAuth;
            }
        }
        throw new IllegalArgumentException("Unknown enum type " + source);
    }
}
