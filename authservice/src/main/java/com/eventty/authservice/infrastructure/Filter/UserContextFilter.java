package com.eventty.authservice.infrastructure.Filter;

import com.eventty.authservice.infrastructure.context.UserContext;
import com.eventty.authservice.infrastructure.contextholder.UserContextHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Slf4j
public class UserContextFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(UserContextFilter.class);


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 상관 관계 ID
        String corrleationId = request.getHeader(UserContext.CORRELATION_ID);
        UserContextHolder.getContext().setCorrelationId(corrleationId);
        logger.debug("UserContextFilter Correlation id: {}", UserContextHolder.getContext().getCorrelationId());

        // 다음 필터로 넘기기
        filterChain.doFilter(request, response);

        // 모든 로직 마친 후 응답 로그
        logger.debug("Processed all business logic in the user server; Returned correlation ID: {}",
                UserContextHolder.getContext().getCorrelationId());

    }
}
