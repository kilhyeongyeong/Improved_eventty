//package com.eventty.applyservice.presentation;
//
//import org.springframework.security.test.context.support.WithSecurityContext;
//
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//
//@Retention(RetentionPolicy.RUNTIME)
//@WithSecurityContext(factory = WithMockCustomSecurityContextFactory.class)
//public @interface WithMockCustom {
//    String principal() default "3";
//
//    String credentials() default "";
//
//    String[] authorities() default {};
//}