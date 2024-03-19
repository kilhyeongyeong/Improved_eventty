package com.eventty.authservice.domain.exception;

import com.eventty.authservice.applicaiton.dto.CsrfTokenDTO;
import com.eventty.authservice.global.Enum.ErrorCode;
import com.eventty.authservice.global.exception.AuthException;

public class InvalidCsrfTokenException extends AuthException {
    private static final String[] fields = {"userId", "csrfToken"};
    public InvalidCsrfTokenException(CsrfTokenDTO csrfTokenDTO) {
        super(ErrorCode.INVALID_CSRF_TOKEN, csrfTokenDTO, fields);
    }
}
