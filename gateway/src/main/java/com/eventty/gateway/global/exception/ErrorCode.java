package com.eventty.gateway.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
    // 나중에 필요 없는 것 주석 처리할 예정
    INTERNAL_ERROR(500, "", "Internal server error"),
    PAGE_NOT_FOUND(404, "", "Not Found"),
    DATA_NOT_FOUND(400, "", "Data not found"),
    METHOD_NOT_ALLOWED(405, "", "Method not allowed"),
    INVALID_INPUT_VALUE(400, "", "Invalid input type"),
    INVALID_TYPE_VALUE(400, "", "Invalid type value"),
    BAD_CREDENTIALS(400, "", "Bad credentials"),
    BAD_REQUEST(400, "", "Bad Request"),
    DATABASE_CONSTRAINT_VIOLATION(400, "", "Database constraint violation"),
    REFERENCE_INTEGRITY_VIOLATION(400, "" , "Reference integrity violation"),
    DATA_SIZE_VIOLATION(400, "", "Data size exceeds limit"),
    CONFLICT(409, "", "Conflict occurred"),
    PERMISSION_DENIED(403, "", "Permission Denied"),

    // 400: 잘못된 요청 401: 유효한 자격 증명 없음 403: 서버에 요청이 전달되었지만, 권한 떄문에 거절
    // 404: 리소스 없는 경우 405: 요청에 지정된 방법 사용 X, 409: 서버의 현재 상태와 요청이 충돌
    AUTH_SERVER_RESPONSE_ERROR(500, "", "Auth server response error"),

    // Authenticaiton
    FAIL_AUTHENTICATION(403, "G000", "Fail Authentication"),
    NO_ACCESS_TOKEN(401, "G000", "No Access Token"),
    NO_CSRF_TOKEN(401, "G000", "No CSRF Token");

    private final int status;
    private final String code;
    private final String message;
}
