package com.eventty.userservice.presentation.exception;

import com.eventty.userservice.domain.UserEntity;
import com.eventty.userservice.domain.exception.UserException;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.lang.reflect.Field;
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
                            sb.append("Field(").append(error.getField()).append("): ");
                            sb.append(error.getRejectedValue() == null ? "" : error.getRejectedValue().toString());
                            sb.append(" // Meesage: ");
                            sb.append(error.getDefaultMessage());
                            sb.append("\n");
                        }
                );
        log.error(sb.toString());
    }

    // 데이터베이스 제약 조건이 위반될 때 발생 (Entity 필드 유효성 검사나 데이터베이스 테이블의 unique 제약 조건 등이 위반될 경우 발생)
    // ConstraintViolation을 기반으로 오류 정보 출력  => @Validated ( @Requestparams, @PathVariable, ...) and DB Entity Validation
    public void logging(final Set<ConstraintViolation<?>> constraintViolations) {
        StringBuffer sb = new StringBuffer();
        sb.append("ConstraintViolation Result Details: \n");
        constraintViolations.stream()
                .forEach(error -> {
                            sb.append(error.getPropertyPath().toString()).append(": ");
                            sb.append(error.getInvalidValue().toString()).append(" and Meesage: ");
                            sb.append(error.getMessage());
                            sb.append("\n");
                        }
                );
        log.error(sb.toString());
    }

    public void logging(UserException e) {
        Object causedErrorData = e.getCausedErrorData();

        // 기본생성자일 경우
        if(e.getFields() == null && causedErrorData == null)
            return;

        StringBuffer sb = new StringBuffer("Input Data Details :\n");
        for(String field : e.getFields()){
            sb.append(field).append("=");
            try {
                // causedErrorData가 DTO or Entity 일 경우 해당 필드값 매핑
                Field fieldName = causedErrorData.getClass().getDeclaredField(field);
                fieldName.setAccessible(true);
                sb.append(fieldName.get(causedErrorData));
            }catch (Exception error){
                // causedErrorData가 Wrapper Class일 경우 causedErrorData 매핑
                sb.append(causedErrorData);
            }
            sb.append("\n");
        }
        log.error(sb.toString());
    }
}
