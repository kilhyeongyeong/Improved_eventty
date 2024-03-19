package com.eventty.authservice.infrastructure.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiSuccessData {
    Class<?> value() default Void.class;
    String stateCode() default "200";
    boolean isArray() default false;
}
