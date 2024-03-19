package com.eventty.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Order(1)
@Component
public class TrakingFilter implements GlobalFilter {
    private static final Logger logger = LoggerFactory.getLogger(TrakingFilter.class);

    FilterUtils filterUtils;

    @Autowired
    public TrakingFilter (FilterUtils filterUtils) {
        this.filterUtils = filterUtils;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();

        // 헤더에 상관 관계 ID가 있다면
        if (isCorrlationIdPresent(requestHeaders)) {
            logger.debug("{} found in tracking filter: {}.",
                    FilterUtils.CORRELATION_ID, filterUtils.getCorrelationId(requestHeaders));
        } else {
            String correlationId = generateCorrelationId();
            exchange = filterUtils.setCorrlationId(exchange, correlationId);
            logger.debug("{} generated in tracking filter: {}.",
                    FilterUtils.CORRELATION_ID, correlationId);
        }

        return chain.filter(exchange);
    }

    private boolean isCorrlationIdPresent(HttpHeaders requestHeaders) {
        return filterUtils.getCorrelationId(requestHeaders) != null;
    }

    private String generateCorrelationId() {
        return java.util.UUID.randomUUID().toString();
    }
}
