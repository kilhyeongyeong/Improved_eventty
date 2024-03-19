package com.eventty.businessservice.event.infrastructure.config;

import com.eventty.businessservice.event.infrastructure.filter.LoggerFilter;
import com.eventty.businessservice.event.infrastructure.filter.UserContextFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    private final ObjectMapper objectMapper;

    @Autowired
    public FilterConfig (ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public FilterRegistrationBean<LoggerFilter> loggingFilter() {
        FilterRegistrationBean<LoggerFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new LoggerFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);       // 가장 맨 처음 Filter로 등록

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<UserContextFilter> userContextFilter() {
        FilterRegistrationBean<UserContextFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new UserContextFilter(objectMapper));
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(2);

        return registrationBean;
    }
}
