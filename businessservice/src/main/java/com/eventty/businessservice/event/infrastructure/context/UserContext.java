package com.eventty.businessservice.event.infrastructure.context;

import com.eventty.businessservice.event.domain.model.Authority;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    public Long userIdTypeLong() { return Long.parseLong(userId); }
}

