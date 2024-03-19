package com.eventty.authservice.infrastructure.config;

import com.eventty.authservice.infrastructure.utils.StringToOAuthConverter;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.eventty.authservice.infrastructure.utils.StringToUserRoleConverter;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToUserRoleConverter());
        registry.addConverter(new StringToOAuthConverter());
    }
}
