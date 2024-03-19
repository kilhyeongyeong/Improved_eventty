package com.eventty.businessservice.event.api.config;

import com.eventty.businessservice.event.api.interceptor.UserContextInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

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