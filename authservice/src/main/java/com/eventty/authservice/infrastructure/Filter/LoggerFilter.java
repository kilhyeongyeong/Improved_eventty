package com.eventty.authservice.infrastructure.Filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class LoggerFilter extends OncePerRequestFilter {

    private final String HEADER_USER_ID = "X-User-Id";
    private final String HEADER_AUTHORITIES = "X-User-Authorities";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // URL & Method
        log.debug("This request URL is {} and method is {}", request.getRequestURL(), request.getMethod());
        // Authentication
        log.debug("Request Header have user Id and authorities ? {} || {}",
                (request.getHeader(HEADER_USER_ID) != null), (request.getHeader(HEADER_AUTHORITIES) != null));

        filterChain.doFilter(request, response);
    }
}
