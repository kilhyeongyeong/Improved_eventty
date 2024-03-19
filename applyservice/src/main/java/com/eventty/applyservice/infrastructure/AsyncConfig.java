package com.eventty.applyservice.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {
    @Bean(name = "asyncExecutor")
    public TaskExecutor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(8);       // 코어 스레드 개수
        executor.setMaxPoolSize(25);        // 최대 스레드 개수
        executor.setQueueCapacity(1000);     // 대기 큐 용량
        executor.setThreadNamePrefix("Async-");
        executor.initialize();
        return executor;
    }
}
