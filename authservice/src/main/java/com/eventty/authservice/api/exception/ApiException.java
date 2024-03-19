package com.eventty.authservice.api.exception;

import com.eventty.authservice.global.response.ResponseDTO;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;

import java.net.URI;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private String message;
    private HttpStatusCode HttpStatusCode;
    public ApiException(URI uri, HttpMethod httpMethod, HttpStatusCode HttpStatusCode) {
        this.message = "API 호출 실패 URI: " + uri + ", Method: " + httpMethod;
        this.HttpStatusCode = HttpStatusCode;
    }
}
