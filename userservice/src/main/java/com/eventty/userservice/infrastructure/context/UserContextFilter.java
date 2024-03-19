package com.eventty.userservice.infrastructure.context;

import com.eventty.userservice.domain.Authority;
import com.eventty.userservice.domain.exception.PermissionDeniedException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

// 해당 클래스에서 @Component를 등록하게 되면 해당 클래스가 빈으로 등록되는데,
// FilterConfig에서 선언해둔 userContextFilter랑 같은 이름이라 충돌이 일어납니다.
// FilterConfig에 메서드이름을 바꾸면 해결되겠지만, 아마도 제 생각에는 저것을 이용해서 빈으로 등록해야 저 안에서 설정한 것들이 유효하지 않을까 싶네요.
@Slf4j
public class UserContextFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(UserContextFilter.class);

    private ObjectMapper objectMapper;

    public UserContextFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 상관 관계 ID는 무조건 담겨서 옴
        String correlationId = request.getHeader(UserContext.CORRELATION_ID);
        UserContextHolder.getContext().setCorrelationId(correlationId);
        logger.debug("UserContextFilter Correlation id: {}", UserContextHolder.getContext().getCorrelationId());

        // userId랑 authorities의 경우, 담겨 올수도 있고, 안담겨 올 수도 있다.
        String userId = request.getHeader(UserContext.USER_ID);
        String authoritiesJson = request.getHeader(UserContext.AUTHORITIES);

        // userId 혹은 권한이 담겨온 경우에만 로직 실행
        if (userId != null && !userId.isEmpty() && authoritiesJson != null && !authoritiesJson.isEmpty()) {
            // JSON 형태의 문자열 => 모든 권한 변환
            List<Authority> authorities = jsonParse(authoritiesJson);
            // List<Authority> authorities = generateGrantesAuthories(jsonParse(authoritiesJson));

            // User 정보 저장
            UserContextHolder.getContext().setUserId(userId);
            UserContextHolder.getContext().setAuthorities(authorities);

            // User Context 초기화 성공 로그
            logger.debug("Successfully initialized UserContext");
        }
        // 다음 필터로 넘기기
        filterChain.doFilter(request, response);

        // 모든 로직 마친 후 응답 로그
        logger.debug("Processed all business logic in the user server; Returned correlation ID: {}",
                UserContextHolder.getContext().getCorrelationId());
    }


  private List<Authority> jsonParse(String authJSONString) {
        try {
            return objectMapper.readValue(authJSONString, new TypeReference<List<Authority>>() {});
        } catch (Exception e) {
            log.error("Error converting JSON to authorities", e); // 예외 정보도 함께 로깅하여 추후 디버깅 시 편리함을 제공합니다.
            throw new PermissionDeniedException();
        }
    }

    /**
     * json형태의 String을 List<Map> 으로 변환
     *
     * @param authJSONString
     * @return List<Map<String, String>>
     */
/*    private List<Map<String, String>> jsonParse(String authJSONString){
        try{
            return objectMapper.readValue(authJSONString, List.class);
        }catch (Exception e){
            log.error("Error converting JSON to authorities");
            throw new PermissionDeniedException();
        }
    }*/

/*    *//**
     * json에서 parsing한 값에서 authority만 가져와서 List로 반환
     *
     * @param authorities
     * @return List<GrantedAuthority>
     *//*
    private List<Authority> generateGrantesAuthories(List<Map<String, String>> authorities){
        List<Authority> grantedAuthorities = new ArrayList<>();

        for(Map<String, String> auth : authorities){
            String role = auth.get("role");
            if(role != null && !"".equals(role)){
                grantedAuthorities.add(new Authority(role));
            }
        }

        return grantedAuthorities;
    }*/

}
