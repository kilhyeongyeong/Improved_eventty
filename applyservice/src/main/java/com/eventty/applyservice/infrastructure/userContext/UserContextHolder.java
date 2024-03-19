package com.eventty.applyservice.infrastructure.userContext;

import com.eventty.applyservice.infrastructure.userContext.UserContext;

public class UserContextHolder {

    // ThreadLocal로 등록하면서 각 스레드는 자신만의 독립적인 UserContext 인스턴스를 사용
    // 이는 멀티 스레드 환경에서 UserContext의 상태를 스레드별로 독립적으로 관리
    private static final ThreadLocal<UserContext> userContext =
            ThreadLocal.withInitial(UserContext::new);

    public static UserContext getContext() {
        return userContext.get();
    }
}