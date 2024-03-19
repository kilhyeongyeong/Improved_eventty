package com.eventty.businessservice.event.presentation.response;

import com.eventty.businessservice.event.domain.Enum.ErrorCode;
import lombok.Getter;

/** 실패 시 응답 객체 예시
{
    "success": false,
    "code": "C004",
    "message": "invalid type value",
    "error" : []
}
 */

@Getter
public class ErrorResponseDTO {

    private String code;

    private ErrorResponseDTO(ErrorCode errorcode) {
        this.code = errorcode.getCode();
    }

    private ErrorResponseDTO(String code) { this.code = code; }

    /*
    정적 팩토리 메소드 of : 입력 매개변수에 따라 유연하게 ErrorResponse 객체를 반환하므로써 다양한 예외처리에 대응
     */

    public static ErrorResponseDTO of(final ErrorCode errorcode) {
        return new ErrorResponseDTO(errorcode);
    }

    public static ErrorResponseDTO of(final String code) { return new ErrorResponseDTO(code); }
}