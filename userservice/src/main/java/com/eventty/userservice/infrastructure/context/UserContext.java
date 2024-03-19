package com.eventty.userservice.infrastructure.context;

import com.eventty.userservice.domain.Authority;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

// Component로 등록해 두면서 싱글톤 객체로 생성됨
@Component
@Getter
@Setter
public class UserContext {
    public static final String CORRELATION_ID   = "X-Correlation-ID";
    public static final String USER_ID          = "X-User-Id";
    public static final String AUTHORITIES      = "X-Authorities";

    private String correlationId = new String();
    private String userId = new String();
    private List<Authority> authorities = new ArrayList<>();
}
