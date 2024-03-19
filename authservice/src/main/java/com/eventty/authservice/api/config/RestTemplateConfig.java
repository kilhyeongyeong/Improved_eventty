package com.eventty.authservice.api.config;

import com.eventty.authservice.api.interceptor.UserContextInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Configuration
public class RestTemplateConfig {

    private final CustomRestTemplateErrorHandler customRestTemplateErrorHandler;
    private final ObjectMapper objectMapper;

    @Bean
    public RestTemplate customRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(customRestTemplateErrorHandler);
        restTemplate.getInterceptors().add(new UserContextInterceptor(objectMapper));
        return restTemplate;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
                .additionalMessageConverters(new StringHttpMessageConverter(StandardCharsets.UTF_8))
                .build();
    }
}
