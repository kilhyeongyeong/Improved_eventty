package com.eventty.authservice.global.Enum;

import com.eventty.authservice.domain.Enum.OAuth;
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

    // Auth
    // G000 => 재로그인 요구
    // 400: 잘못된 요청 401: 유효한 자격 증명 없음 403: 서버에 요청이 전달되었지만, 권한 떄문에 거절
    USER_NOT_FOUND(400, "", "User Not Found Exception"),
    INVALID_PASSWORD(400, "", "Invalid Password Exception"),
    ACCESS_DELETED_USER(400, "", "User has been deactivated."),

    USER_ROLE_NOT_FOUND(401, "", "User Role Not Found."),
    INVALID_REFRESH_TOKEN(401, "G000", "Invalid Refresh Token Exception"),
    REFRESH_TOKEN_NOT_FOUND(401, "G000", "Refresh Token Not Found Exception"),
    CSRF_TOKEN_NOT_FOUND(401, "G000", "Csrf Token Not Found Exception"),
    INVALID_CSRF_TOKEN(401, "G000", "Invalid CSRF Token Exception"),

    DUPLICATE_EMAIL(409, "", "Duplicate Email"),

    SESSION_IS_EXPIRED_RESET_PASSWORD(400, "", "Password reset request failed due to expired session"),

    // OAuth
    OAUTH_NOT_FOUND_USER_INFO(400, "", "OAuth - Failed to request OAuth user info"),
    OAUTH_NOT_FOUND_VERIFIED_EMAIL(400, "", "OAuth - Failed to find verified email"),
    OAUTH_FAIL_GET_ACCESS_TOKEN(400, "", "OAuth - Failed to request OAuth Access Token");

    private final int status;
    private final String code;
    private final String message;
}
