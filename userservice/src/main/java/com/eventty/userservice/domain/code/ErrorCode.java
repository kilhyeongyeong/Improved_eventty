package com.eventty.userservice.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
    DATA_ACCESS_ERROR   (HttpStatus.INTERNAL_SERVER_ERROR.value(),  "", "Data access error"),   // 500
    NOT_FOUND           (HttpStatus.NOT_FOUND.value(), "", "Not Found"),                        // 404
    METHOD_NOT_ALLOWED  (HttpStatus.METHOD_NOT_ALLOWED.value(),     "", "Method not allowed"),  // 405
    INTERNAL_ERROR      (HttpStatus.BAD_REQUEST.value(), "", "Internal server error"),          // 400
    INVALID_INPUT_VALUE (HttpStatus.BAD_REQUEST.value(), "", "Invalid input type"),             // 400
    INVALID_TYPE_VALUE  (HttpStatus.BAD_REQUEST.value(), "", "Invalid type value"),             // 400
    BAD_CREDENTIALS     (HttpStatus.BAD_REQUEST.value(), "", "Bad credentials"),                // 400
    INVALID_JSON        (HttpStatus.BAD_REQUEST.value(), "", "JSON parse error"),               // 400
    PERMISSION_DENIED   (HttpStatus.FORBIDDEN.value(), "", "Permission Denided"),               // 403

    // User
    USER_INFO_NOT_FOUND (HttpStatus.BAD_REQUEST.value(), "U001", "User information does not exist"),    // 400
    USER_ID_DUPLICATE   (HttpStatus.BAD_REQUEST.value(), "U002", "UserId already exist"),               // 400
    CONTENTTYPE_ERROR   (HttpStatus.BAD_REQUEST.value(), "U003", "Unsupported mediaContentType");       // 400

    private final int status;
    private final String code;
    private final String message;
}