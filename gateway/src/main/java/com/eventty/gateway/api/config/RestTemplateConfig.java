package com.eventty.gateway.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Configuration
public class RestTemplateConfig {

    private final CustomRestTemplateErrorHandler customRestTemplateErrorHandler;

    @Bean
    public RestTemplate basicRestTemplate() { return new RestTemplate(); }

    @Bean
    public RestTemplate customRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(customRestTemplateErrorHandler);
        return restTemplate;
    }
}
