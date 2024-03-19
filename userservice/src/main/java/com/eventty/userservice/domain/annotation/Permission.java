package com.eventty.userservice.domain.annotation;

import com.eventty.userservice.domain.code.UserRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {
    UserRole[] Roles() default {};
}