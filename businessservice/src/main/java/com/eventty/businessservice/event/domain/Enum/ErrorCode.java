package com.eventty.businessservice.event.domain.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
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

    // Event & Ticket
    EVENT_NOT_FOUND(404, "E001", "Event is not found"),
    TICKET_NOT_FOUND(404, "E002", "Ticket is not found"),
    CONTENT_TYPE_ERROR(400, "E003", "Content type is not supported"),

    // Host
    ACCESS_DENIED(403, "U001", "Access denied since you are not the host of this event"),
    HOST_INFO_NOT_FOUND(404, "U002", "Host information is not found");

    private final int status;
    private final String code;
    private final String message;

}