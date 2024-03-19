package com.eventty.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

@Configuration
public class ResponseFilter {
    final Logger logger = LoggerFactory.getLogger(ResponseFilter.class);

    FilterUtils filterUtils;

    @Autowired
    public ResponseFilter (FilterUtils filterUtils) {
        this.filterUtils = filterUtils;
    }

    @Bean
    public GlobalFilter postGlobalFilter() {
        return ((exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(() -> {
            HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
            String correlationId = filterUtils.getCorrelationId(requestHeaders);

            logger.debug("Completed the request with correlation ID {}", correlationId);

            logger.debug("Completing outgoing request for {}.", exchange.getRequest().getURI());
        })));
    }
}
