package com.eventty.businessservice.event.presentation.utils;

import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Set;

@Slf4j
@Service
public class DataErrorLogger {

    // BindingResult를 기반으로 오류 정보 출력
    public void logging(final BindingResult bindingResult) {
        StringBuffer sb = new StringBuffer();
        sb.append("Binding Result Details: \n");
        bindingResult.getFieldErrors().stream()
                .forEach(error -> {
                            sb.append("Field(" + error.getField() + "): ");
                            sb.append(error.getRejectedValue() == null ? "" : error.getRejectedValue().toString());
                            sb.append(" // Meesage: ");
                            sb.append(error.getDefaultMessage());
                            sb.append("\n");
                        }
                );
        System.out.println(sb);
    }

    // 데이터베이스 제약 조건이 위반될 때 발생 (Entity 필드 유효성 검사나 데이터베이스 테이블의 unique 제약 조건 등이 위반될 경우 발생)
    // ConstraintViolation을 기반으로 오류 정보 출력  => @Validated ( @Requestparams, @PathVariable, ...) and DB Entity Validation
    public void logging(final Set<ConstraintViolation<?>> constraintViolations) {
        StringBuffer sb = new StringBuffer();
        sb.append("ConstraintViolation Result Details: \n");
        constraintViolations.stream()
                .forEach(error -> {
                            sb.append(error.getPropertyPath().toString() + ": ");
                            sb.append(error.getInvalidValue().toString());
                            sb.append(" and Meesage: ");
                            sb.append(error.getMessage());
                            sb.append("\n");
                        }
                );
        System.out.println(sb);
    }
}