package com.eventty.gateway.filter;

import com.eventty.gateway.api.ApiClient;
import com.eventty.gateway.api.dto.AuthenticationDetailsResponseDTO;
import com.eventty.gateway.global.dto.ResponseDTO;
import com.eventty.gateway.global.exception.auth.AuthServerResponseErrorException;
import com.eventty.gateway.global.exception.auth.NoAccessTokenException;
import com.eventty.gateway.utils.CustomMappper;
import com.eventty.gateway.utils.TokenEnum;
import com.eventty.gateway.utils.CookieCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    // 의존성 제거
    private final ApiClient apiClient;
    private final CustomMappper customMappper;

    @Autowired
    public JwtAuthenticationFilter(ApiClient apiClient, CustomMappper customMappper) {
        super(Config.class); // Config.class를 매개변수로 전달
        this.apiClient = apiClient;
        this.customMappper = customMappper;
    }

    public static class Config {
        // 만약 필터에 필요한 추가 설정이 있다면 이곳에 속성들을 추가
        // 예: private String someProperty;
        // public String getSomeProperty() { return someProperty; }
        // public void setSomeProperty(String someProperty) { this.someProperty = someProperty; }
    }

    @Override
    public GatewayFilter apply(Config config) {

        // 함수형 인터페이스의 인스턴스를 간결하게 표한하는 람다 표현식
        return ((exchange, chain) -> {
            log.debug("Current Postion:: JWT Authentication Filter");

            MultiValueMap<String, HttpCookie> cookies = exchange.getRequest().getCookies();
            Map<String, List<String>> headers = exchange.getRequest().getHeaders();

            log.debug("Access Token 검증");
            // Access Token 검증
            boolean hasAccessToken = cookies.get(TokenEnum.ACCESS_TOKEN.getName()) != null;
            if (!hasAccessToken)
                throw new NoAccessTokenException();

            log.debug("Auth Server Api Call: Authenticate User");
            // 이 부분이 API 요청 보내는 로직으로 변경되어야 함
            ResponseEntity<ResponseDTO<AuthenticationDetailsResponseDTO>> response = apiClient.authenticateUser(customMappper.authenticateUserRequestDTO(cookies, headers));

            // 예외가 발생하지 않은 상태로 왔는데 데이터가 비어있는 경우 인증 서버에서 문제가 있는 것
            if (response.getBody() == null) {
                throw new AuthServerResponseErrorException();
            }

            AuthenticationDetailsResponseDTO authenticationDetailsResponseDTO = response.getBody().getSuccessResponseDTO().getData();
            log.debug("Auth User Id: {}", authenticationDetailsResponseDTO.getUserId());
            log.debug("Auth User Authorities: {}", authenticationDetailsResponseDTO.getAuthoritiesJSON());

            // Authentication 객체를 직렬화해서 보낼 수 있지만, 데이터의 크기와 복잡성 때문에 각 서비스에서 만드는 것이 효율적
            ServerHttpRequest requestWithHeader = exchange.getRequest().
                    mutate()
                    .header(FilterUtils.USER_ID, authenticationDetailsResponseDTO.getUserId().toString())
                    .header(FilterUtils.AUTHORITIES, authenticationDetailsResponseDTO.getAuthoritiesJSON())
                    .build();

            log.debug("User: {}, Path: {}", authenticationDetailsResponseDTO.getUserId(), exchange.getRequest().getPath());

            return chain.filter(exchange.mutate().request(requestWithHeader).build())
                    .then(Mono.fromRunnable(() -> {
                        log.debug("PostLogger: Authentication Filter update CSRF Token");
                        ServerHttpResponse serverHttpResponse = exchange.getResponse();

                        // JWT와 Refresh Token의 업데이트가 필요한 경우
                        if (authenticationDetailsResponseDTO.isNeedsUpdate()) {
                            log.debug("Updating JWT and Refresh Token in filter");
                            ResponseCookie jwtCookie = CookieCreator.createAccessTokenCookie(authenticationDetailsResponseDTO.getAccessToken());
                            ResponseCookie responseCookie = CookieCreator.createRefreshTokenCookie(authenticationDetailsResponseDTO.getRefreshToken());

                            serverHttpResponse.addCookie(jwtCookie);
                            serverHttpResponse.addCookie(responseCookie);
                        }
                        // CSRF Token은 항상 업데이트
                        serverHttpResponse.getHeaders().set(FilterUtils.CSRF_TOKEN, authenticationDetailsResponseDTO.getCsrfToken());
            }));
        });
    }
}


/* 람다 표현식을 사용하지 않았을 때
return new GatewayFilter() {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // ... 내부 로직 ...
    }
};*/
