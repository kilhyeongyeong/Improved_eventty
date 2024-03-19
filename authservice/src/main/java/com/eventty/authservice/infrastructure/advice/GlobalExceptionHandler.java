package com.eventty.authservice.infrastructure.advice;

import com.eventty.authservice.global.Enum.ErrorCode;
import com.eventty.authservice.global.exception.AuthException;
import com.eventty.authservice.global.utils.DataErrorLogger;
import jakarta.validation.ConstraintViolationException;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.extern.slf4j.Slf4j;

import com.eventty.authservice.global.response.ErrorResponseDTO;
import com.eventty.authservice.api.exception.ApiException;

import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final DataErrorLogger dataErrorLogger;

    @Autowired
    GlobalExceptionHandler (@Lazy DataErrorLogger dataErrorLogger) {
        this.dataErrorLogger = dataErrorLogger;
    }

    // 지원하지 않는 HTTP 요청 메서드에 대한 예외 처리
    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDTO> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("Unsupported HTTP request method: {} / {}", e.getMethod(), e.getMessage());

        final ErrorResponseDTO response = ErrorResponseDTO.of(ErrorCode.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    // 지정하지 않은 API URI 요청이 들어왔을 경우 (404 예외 처리 핸들러 => appication-properties 설정 필요)
    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDTO> handleNotFoundExceptoin(NoHandlerFoundException e) {
        log.error("NoHandlerFoundException occurred: {}", e.getMessage());

        final ErrorResponseDTO response = ErrorResponseDTO.of(ErrorCode.PAGE_NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // @Valid 에서 발생한 binding error 에 대한 예외 처리
    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException occurred: {}", e.getMessage());
        dataErrorLogger.logging(e.getBindingResult());

        final ErrorResponseDTO response = ErrorResponseDTO.of(ErrorCode.INVALID_INPUT_VALUE);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 데이터베이스 제약 조건이 위반될 때 발생 (Entity 필드 유효성 검사나 데이터베이스 테이블의 unique 제약 조건 등이 위반될 경우 발생)
    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDTO> handleBindException(ConstraintViolationException e) {
        log.error("ConstraintViolationException occurred: {}", e.getMessage());
        dataErrorLogger.logging(e.getConstraintViolations());

        final ErrorResponseDTO response = ErrorResponseDTO.of(ErrorCode.INVALID_INPUT_VALUE);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // binding error 대한 예외 처리 (주로 @PathVariable 시 잘못된 type 데이터가 들어왔을 때 에러)
    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDTO> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("MethodArgumentTypeMismatchException occurred: {}", e.getMessage());

        final String value = e.getValue() == null ? "" : e.getValue().toString();
        log.error("Input value is {} and type is {}", value, e.getValue().getClass());

        // 로깅 추가
        log.error("Method name: {}", Objects.requireNonNull(e.getParameter().getMethod()).getName());
        log.error("Parameter name: {}", e.getName());

        final ErrorResponseDTO response = ErrorResponseDTO.of(ErrorCode.INVALID_TYPE_VALUE);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    // 리소스 상태의 충돌로 인해 요청이 완료될 수 경우에 대한 예외 처리
    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDTO> handleConflictException(DataIntegrityViolationException e) {
        log.error("ConstraintViolationException Occurred: {}", e.getMessage());

        final ErrorResponseDTO response = ErrorResponseDTO.of(ErrorCode.CONFLICT);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // JSON을 파싱하다 문제가 발생한 경우(@Valid @Request Body와 형식이 맞지 않는 경우, JSON 형태가 지켜지지 않았을 경우)
    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDTO> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        log.error("HttpMessageNotReadableException occurred: {}", e.getMessage());

        final ErrorResponseDTO response = ErrorResponseDTO.of(ErrorCode.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 서버간의 API 통신에서 발생한 예외 처리 (분기점 X => 상태 코드만 그대로 전달)
    @ExceptionHandler
    protected  ResponseEntity<ErrorResponseDTO> handleApiException(ApiException e) {
        log.error(e.getMessage());

        final ErrorResponseDTO response = ErrorResponseDTO.of("");
        return new ResponseEntity<>(response, e.getHttpStatusCode());
    }

    // 인증 예외 처리 (분기점 발생 최종적으로 여기서 잡아냄)
    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDTO> handleAuthenticationException(AuthException e) {
        log.error("AuthenticationException Occurred: {}", e.getErrorCode().getMessage());
        dataErrorLogger.logging(e);

        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponseDTO response = ErrorResponseDTO.of(errorCode);
        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }

    // 기타 예외 처리
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponseDTO> handleException(Exception e) {
        log.error("Exception Occured: {}", e.getMessage());

        final ErrorResponseDTO response = ErrorResponseDTO.of(ErrorCode.INTERNAL_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /*
    // binding error 에 대한 예외 처리 (주로 MVC 에서 @ModelAttribute 에서 발생)
    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDTO> handleBindException(BindException e) {
        final ErrorResponseDTO response = ErrorResponseDTO.of(INVALID_INPUT_VALUE, e.getBindingResult());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    */


}
