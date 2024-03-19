package com.eventty.businessservice.event.api.exception;

import lombok.Getter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;

import java.net.URI;

@Getter
public class ApiException extends RuntimeException {

    private String message;
    private HttpStatusCode HttpStatusCode;
    public ApiException(URI uri, HttpMethod httpMethod, HttpStatusCode HttpStatusCode) {
        this.message = "API 호출 실패 URI: " + uri + ", Method: " + httpMethod;
        this.HttpStatusCode = HttpStatusCode;
    }

}
