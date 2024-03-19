package com.eventty.businessservice.event.presentation;

import com.eventty.businessservice.event.api.exception.ApiException;
import com.eventty.businessservice.event.domain.exception.BusinessException;
import com.eventty.businessservice.event.presentation.response.ErrorResponseDTO;
import com.eventty.businessservice.event.domain.Enum.ErrorCode;
import com.eventty.businessservice.event.presentation.utils.DataErrorLogger;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import static com.eventty.businessservice.event.domain.Enum.ErrorCode.*;

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
    protected ResponseEntity<ErrorResponseDTO> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("Unsupported HTTP request method: {} / {}", e.getMethod(), e.getMessage());

        final ErrorResponseDTO response = ErrorResponseDTO.of(METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);

        // return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED); (body에 code도 담지 않고 보내는 방식)
    }

    // 지정하지 않은 API URI 요청이 들어왔을 경우 (404 예외 처리 핸들러 => appication-properties 설정 필요)
    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDTO> handleNotFoundExceptoin(NoHandlerFoundException e) {
        log.error("NoHandlerFoundException occurred: {}", e.getMessage());

        final ErrorResponseDTO response = ErrorResponseDTO.of(PAGE_NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // @Valid 에서 발생한 binding error 에 대한 예외 처리
    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException occurred: {}", e.getMessage());
        dataErrorLogger.logging(e.getBindingResult());

        final ErrorResponseDTO response = ErrorResponseDTO.of(INVALID_INPUT_VALUE);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 데이터베이스 제약 조건이 위반될 때 발생 (Entity 필드 유효성 검사나 데이터베이스 테이블의 unique 제약 조건 등이 위반될 경우 발생)
    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDTO> handleBindException(ConstraintViolationException e) {
        log.error("ConstraintViolationException occurred: {}", e.getMessage());
        dataErrorLogger.logging(e.getConstraintViolations());

        final ErrorResponseDTO response = ErrorResponseDTO.of(INVALID_INPUT_VALUE);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // binding error 대한 예외 처리 (주로 @PathVariable 시 잘못된 type 데이터가 들어왔을 때 에러)
    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDTO> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("MethodArgumentTypeMismatchException occurred: {}", e.getMessage());

        final String value = e.getValue() == null ? "" : e.getValue().toString();
        log.error(e.getName() + ": " + value + ", and ErrorCode: " + e.getErrorCode() + "\n");

        final ErrorResponseDTO response = ErrorResponseDTO.of(INVALID_TYPE_VALUE);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 리소스 상태의 충돌로 인해 요청이 완료될 수 경우에 대한 예외 처리
    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDTO> handleConflictException(DataIntegrityViolationException e) {
        log.error("ConstraintViolationException Occurred: {}", e.getMessage());

        final ErrorResponseDTO response = ErrorResponseDTO.of(CONFLICT);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // 인증 예외 처리 (분기점 발생 최종적으로 여기서 잡아냄)
//    @ExceptionHandler
//    protected ResponseEntity<ErrorResponseDTO> handleAuthenticationException(AuthException e) {
//        log.error("AuthenticationException Occurred: {}", e.getErrorCode().getMessage());
//
//        final ErrorCode errorCode = e.getErrorCode();
//        final ErrorResponseDTO response = ErrorResponseDTO.of(errorCode);
//        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
//    }

    // 서버간의 API 통신에서 발생한 예외 처리 (분기점 X => 상태 코드만 그대로 전달)
    @ExceptionHandler
    protected  ResponseEntity<ErrorResponseDTO> handleApiException(ApiException e) {
        log.error(e.getMessage());

        final ErrorResponseDTO response = ErrorResponseDTO.of("");
        return new ResponseEntity<>(response, e.getHttpStatusCode());
    }


    // 비즈니스 요구사항에 따른 예외처리
    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDTO> handleBusinessException(BusinessException e) {
        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponseDTO response = ErrorResponseDTO.of(errorCode);
        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }

    // 기타 예외 처리
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponseDTO> handleException(Exception e) {
        log.error("Exception Occured: {}", e.getMessage());
        final ErrorResponseDTO response = ErrorResponseDTO.of(INTERNAL_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}