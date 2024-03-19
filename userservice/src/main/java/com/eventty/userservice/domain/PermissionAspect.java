package com.eventty.userservice.domain;

import com.eventty.userservice.domain.annotation.Permission;
import com.eventty.userservice.domain.exception.PermissionDeniedException;
import com.eventty.userservice.domain.code.UserRole;
import com.eventty.userservice.infrastructure.context.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
public class PermissionAspect {

    // Controller Method가 실행되기 '직전에만' 실행
    @Before("@annotation(permission)")
    public void checkAuthorization(JoinPoint joinPoint, Permission permission) {
        List<Authority> authorities = UserContextHolder.getContext().getAuthorities();

        // 에러 헨들링
        if (authorities == null || !hasAnyPermission(authorities, permission.Roles()))
            throw new PermissionDeniedException();

        log.info("Successfully verified permission for user {}.", UserContextHolder.getContext().getUserId());
    }

    private boolean hasAnyPermission(List<Authority> authorities, UserRole[] roles) {
        Set<String> requiredAuthorities = Arrays.stream(roles)
                .map(UserRole::getRole)
                .collect(Collectors.toSet());

        Set<String> userAuthorities = authorities.stream()
                .map(Authority::getRole)
                .collect(Collectors.toSet());

        // userAuthorities와 requiredAuthorities의 교집합이 있으면 true 반환
        return userAuthorities.stream().anyMatch(requiredAuthorities::contains);
    }
}