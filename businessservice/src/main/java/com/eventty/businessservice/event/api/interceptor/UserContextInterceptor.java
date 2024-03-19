package com.eventty.businessservice.event.api.interceptor;

import com.eventty.businessservice.event.infrastructure.context.UserContext;
import com.eventty.businessservice.event.infrastructure.contextholder.UserContextHolder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class UserContextInterceptor implements ClientHttpRequestInterceptor {
    private static final String AUTH_HEADER = "X-Requires-Auth";
    private static final Logger looger = LoggerFactory.getLogger(UserContextInterceptor.class);

    private final ObjectMapper objectMapper;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        HttpHeaders headers = request.getHeaders();
        headers.add(UserContext.CORRELATION_ID, UserContextHolder.getContext().getCorrelationId());

        looger.debug("API CALL by correlation id: {}", UserContextHolder.getContext().getCorrelationId());

        if ("True".equals(headers.getFirst(AUTH_HEADER))) {
            // Request Header에 상관관계 ID, User Id, User Authority담아서 보내기
            // 단, 권한은 Json으로 변환 후 보내줘야 함.
            String authoritiesJson;
            try {
                authoritiesJson = objectMapper.writeValueAsString(UserContextHolder.getContext().getAuthorities());
            } catch (JsonProcessingException e) {
                throw  new RuntimeException("Error Converting authorities to Json", e);
            }

            headers.add(UserContext.CORRELATION_ID, UserContextHolder.getContext().getCorrelationId());
            headers.add(UserContext.USER_ID, UserContextHolder.getContext().getUserId());
            headers.add(UserContext.AUTHORITIES, authoritiesJson);
        } else {
            headers.add(UserContext.CORRELATION_ID, UserContextHolder.getContext().getCorrelationId());
        }

        return execution.execute(request, body);
    }
}

