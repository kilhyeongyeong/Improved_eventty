package com.eventty.applyservice.infrastructure.resttemplate;
import com.eventty.applyservice.domain.exception.ApiException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.net.URI;

@Component
public class CustomRestTemplateErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        // 사용자 정의 코드 499 => 비지니스 로직에서 분기점 처리해야 될 때 사용
        if (response.getStatusCode() == HttpStatusCode.valueOf(499)) return false;

        // 4XX, 5XX 상태 코드의 경우 곧바로 GlobalExceptionHandler가 잡도록 구성
        if (response.getStatusCode().is4xxClientError()
                || response.getStatusCode().is5xxServerError()) return true;

        return false;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
    }

    @Override
    public void handleError(URI uri, HttpMethod method, ClientHttpResponse response) throws IOException {
        throw new ApiException(uri, method, response.getStatusCode());
    }
}