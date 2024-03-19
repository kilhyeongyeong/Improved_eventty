package com.eventty.applyservice.infrastructure;

import com.eventty.applyservice.infrastructure.resttemplate.CustomRestTemplateErrorHandler;
import com.eventty.applyservice.infrastructure.userContext.UserContextInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class RestTemplateConfig {

    private final CustomRestTemplateErrorHandler customRestTemplateErrorHandler;
    private final ObjectMapper objectMapper;

    @Bean
    public RestTemplate basicRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RestTemplate customRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(customRestTemplateErrorHandler);
        restTemplate.getInterceptors().add(new UserContextInterceptor(objectMapper));
        return restTemplate;
    }
}