package com.eventty.businessservice.event.domain.annotation;

import com.eventty.businessservice.event.domain.Enum.ErrorCode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiErrorCode {
    ErrorCode[] value();
}