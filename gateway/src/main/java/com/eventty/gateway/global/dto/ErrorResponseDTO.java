package com.eventty.gateway.global.dto;

import com.eventty.gateway.global.exception.ErrorCode;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
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
