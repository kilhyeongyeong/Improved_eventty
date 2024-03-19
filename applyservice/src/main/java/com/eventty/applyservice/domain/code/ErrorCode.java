package com.eventty.applyservice.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Common
    NOT_FOUND           (HttpStatus.NOT_FOUND.value(), "", "Not Found"),                        // 404
    METHOD_NOT_ALLOWED  (HttpStatus.METHOD_NOT_ALLOWED.value(),     "", "Method not allowed"),  // 405
    INTERNAL_ERROR      (HttpStatus.BAD_REQUEST.value(), "", "Internal server error"),          // 400
    INVALID_INPUT_VALUE (HttpStatus.BAD_REQUEST.value(), "", "Invalid input type"),             // 400
    INVALID_JSON        (HttpStatus.BAD_REQUEST.value(), "", "JSON parse error"),               // 400
    PERMISSION_DENIED   (HttpStatus.FORBIDDEN.value(), "", "Permission Denided"),               // 403

    // apply
    ALREADY_APPLY_USER  (HttpStatus.BAD_REQUEST.value(), "A001", "Already applied a user"),
    EXCEED_APPLICANTS   (HttpStatus.BAD_REQUEST.value(), "A002", "Exceeded the number of applicants"),
    NON_EXISTENT_ID       (HttpStatus.BAD_REQUEST.value(), "A003", "Non existent applyId"),
    ALREADY_CANCELED_APPLY(HttpStatus.BAD_REQUEST.value(), "A004", "ApplyId already canceled");

    private final int status;
    private final String code;
    private final String message;
}
