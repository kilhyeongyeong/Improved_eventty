package com.eventty.gateway.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
// Json으로 Response를 보낼 때, 필드가 null 값일 경우 그 필드 제외하고 보냄.
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class ResponseDTO<T> {

    private boolean isSuccess;
    private ErrorResponseDTO errorResponseDTO;
    private SuccessResponseDTO<T> successResponseDTO;

    private ResponseDTO(ErrorResponseDTO errorResponseDTO) {
        this.isSuccess = false;
        this.errorResponseDTO = errorResponseDTO;
        this.successResponseDTO = null;
    }

    private ResponseDTO(SuccessResponseDTO<T> successResponseDTO) {
        this.isSuccess = true;
        this.errorResponseDTO = null;
        this.successResponseDTO = successResponseDTO;
    }

    private ResponseDTO(boolean isSuccess) {
        this.isSuccess = isSuccess;
        this.errorResponseDTO = null;
        this.successResponseDTO = null;
    }

    public static ResponseDTO<Void> of(ErrorResponseDTO errorResponseDTO) {
        return new ResponseDTO<>(errorResponseDTO);
    }

    public static <T> ResponseDTO<T> of(SuccessResponseDTO<T> successResponseDTO) {
        return new ResponseDTO<T>(successResponseDTO);
    }

    public static ResponseDTO<Void> of(boolean isSuccess) {
        return new ResponseDTO<>(isSuccess);
    }

}