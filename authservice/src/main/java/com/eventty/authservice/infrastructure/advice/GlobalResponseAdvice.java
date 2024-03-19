package com.eventty.authservice.infrastructure.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.eventty.authservice.global.response.ErrorResponseDTO;
import com.eventty.authservice.global.response.ResponseDTO;
import com.eventty.authservice.global.response.SuccessResponseDTO;

@Slf4j
@RestControllerAdvice(basePackages = "com.eventty")
public class GlobalResponseAdvice implements ResponseBodyAdvice {

    // ResponseDTO로 오는 경우 외에 전부 실행
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return !ResponseDTO.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        log.debug("Current Postion:: Response Advice");

        if (body instanceof SuccessResponseDTO<?>) {
            log.debug("Body have success response dto");
            SuccessResponseDTO successResponseDTO = (SuccessResponseDTO) body;
            return ResponseDTO.of(successResponseDTO);
        }

        if (body instanceof ErrorResponseDTO) {
            log.debug("Body have error response dto");
            ErrorResponseDTO errorResponseDTO = (ErrorResponseDTO) body;
            return ResponseDTO.of(errorResponseDTO);
        }
        if (body instanceof Boolean) {
            log.debug("Body don't have dto");
            Boolean isSuccess = (Boolean) body;
            return ResponseDTO.of(isSuccess);
        }

        return body;
    }
}

