package com.eventty.applyservice.infrastructure;

import org.apache.ibatis.type.LocalDateTimeTypeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConfig {
    @Bean
    public LocalDateTimeTypeHandler localDateTimeTypeHandler() {
        return new LocalDateTimeTypeHandler();
    }
}
