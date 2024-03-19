package com.eventty.applyservice.infrastructure.logger;

import com.eventty.applyservice.infrastructure.userContext.UserContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class LoggerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Check User
        String userId = request.getHeader(UserContext.USER_ID);
        boolean hasUserId = userId != null && !userId.isEmpty();
        log.debug("Is User ID present? {}", hasUserId);

        if (hasUserId) {
            // User ID
            log.debug("USER ID: {}", request.getHeader(UserContext.USER_ID));
        }

        // URL & Method
        log.debug("URL: {} Method: {}\n", request.getRequestURL(), request.getMethod());

        filterChain.doFilter(request, response);
    }
}
