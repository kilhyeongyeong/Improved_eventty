package com.eventty.authservice.infrastructure.config;

import com.eventty.authservice.infrastructure.Filter.LoggerFilter;
import com.eventty.authservice.infrastructure.Filter.UserContextFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConifg {

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

        registrationBean.setFilter(new UserContextFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(2);

        return registrationBean;
    }
}
