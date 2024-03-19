package com.eventty.userservice.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@Getter @NoArgsConstructor
public class ResponseDTO<T> {

    private Boolean isSuccess;
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