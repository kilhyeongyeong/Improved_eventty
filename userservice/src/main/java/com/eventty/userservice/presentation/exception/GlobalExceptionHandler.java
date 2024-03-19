package com.eventty.userservice.presentation.exception;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.eventty.userservice.domain.exception.UserException;
import com.eventty.userservice.presentation.dto.ErrorResponseDTO;
import com.eventty.userservice.presentation.exception.DataErrorLogger;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;

import static com.eventty.userservice.domain.code.ErrorCode.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private DataErrorLogger dataErrorLogger;

    @Autowired
    GlobalExceptionHandler (@Lazy DataErrorLogger dataErrorLogger) {
        this.dataErrorLogger = dataErrorLogger;
    }

    // 지원하지 않는 HTTP 요청 메서드에 대한 예외 처리
    @ExceptionHandler
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    protected ErrorResponseDTO handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("Unsupported HTTP request method: {} / {}", e.getMethod(), e.getMessage());
        return ErrorResponseDTO.of(METHOD_NOT_ALLOWED);
    }

    // 지정하지 않은 API URI 요청이 들어왔을 경우 (404 예외 처리 핸들러 => appication-properties 설정 필요)
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorResponseDTO handleNotFoundExceptoin(NoHandlerFoundException e) {
        log.error("NoHandlerFoundException occurred: {}", e.getMessage());
        return ErrorResponseDTO.of(NOT_FOUND);
    }

    // @Validated 에서 발생한 binding error 에 대한 예외 처리(RequestParams, PathVariable의 유효성 검증 실패 및 DB관련)
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponseDTO handleConstraintViolationException(ConstraintViolationException e) {
        log.error("ConstraintViolationException occurred: {}", e.getMessage());
        dataErrorLogger.logging(e.getConstraintViolations());
        return ErrorResponseDTO.of(INVALID_INPUT_VALUE);
    }

    // @Valid 에서 조건이 맞지 않았을 경우 (DTO, Entity 유효성 검증)
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponseDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException occurred: {}", e.getMessage());
        dataErrorLogger.logging(e.getBindingResult());
        return ErrorResponseDTO.of(INVALID_INPUT_VALUE);
    }

    // JSON을 파싱하다 문제가 발생한 경우(@Valid @Request Body와 형식이 맞지 않는 경우, JSON 형태가 지켜지지 않았을 경우)
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponseDTO handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        log.error("HttpMessageNotReadableException occurred: {}", e.getMessage());
        return ErrorResponseDTO.of(INVALID_JSON);
    }

    // NCP - ObjectStorage 예외처리
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponseDTO handleAmazonS3Exception(AmazonS3Exception e){
        log.error("AmazonS3Exception occured: {}", e.getMessage());
        return ErrorResponseDTO.of(INTERNAL_ERROR);
    }

    // NCP - ObjectStorage 예외처리
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponseDTO handleSdkClientException(SdkClientException e){
        log.error("SdkClientException occured: {}", e.getMessage());
        return ErrorResponseDTO.of(INTERNAL_ERROR);
    }

    // 유저 정보가 존재하지 않을 경우 예외처리
    @ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleUserException(UserException e) {
        log.error("UserException occurred : {}", e.getErrorCode().getMessage());
        dataErrorLogger.logging(e);

        return ErrorResponseDTO.of(e.getErrorCode());
    }

    // 기타 예외처리
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorResponseDTO handleException(Exception e) {
        log.error("Exception occurred : {}", e.getMessage());
        return ErrorResponseDTO.of(INTERNAL_ERROR);
    }
}
