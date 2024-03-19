package com.eventty.gateway.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

@Component
public class FilterUtils {

    public static final String CORRELATION_ID   = "X-Correlation-ID";
    public static final String USER_ID          = "X-User-Id";
    public static final String CSRF_TOKEN       = "X-Csrf-Token";
    public static final String AUTHORITIES      = "X-Authorities";

    public String getCorrelationId(HttpHeaders requestHeaders) {
        if (requestHeaders.get(CORRELATION_ID) != null) {
            List<String> header = requestHeaders.get(CORRELATION_ID);
            return header.stream().findFirst().get();
        }
        else {
            return null;
        }
    }

    public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
        return exchange.mutate().request(
                exchange.getRequest().mutate()
                        .header(name, value)
                        .build())
                .build();
    }

    public ServerWebExchange setCorrlationId(ServerWebExchange exchange, String correlationId) {
        return this.setRequestHeader(exchange, CORRELATION_ID, correlationId);
    }
}
