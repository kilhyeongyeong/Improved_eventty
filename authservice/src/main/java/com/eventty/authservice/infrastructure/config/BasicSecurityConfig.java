package com.eventty.authservice.infrastructure.config;

import com.eventty.authservice.applicaiton.service.utils.token.TokenEnum;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class BasicSecurityConfig {

//    private final String test = "test";
//
//    @Value("${spring.profiles.active}")
//    private String activeProfile;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){return new BCryptPasswordEncoder(); }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                // 1. 권한 설정
                .authorizeHttpRequests(authorizationConfig -> {
                    // "test" 프로파일일 경우 H2 콘솔 접근을 허용합니다.
/*                    if (test.equals(activeProfile)) {
                        authorizationConfig
                                .requestMatchers(PathRequest.toH2Console()) // H2 콘솔 접근 허용
                                .permitAll();
                    }*/
                    authorizationConfig
                            .anyRequest()  // 모든 요청에 대해서
                            .permitAll();  // 허용합니다.
                })

                // 2. 로그인 설정
                .formLogin(AbstractHttpConfigurer::disable)  // 폼 기반 로그인을 비활성화합니다.

                // 3. 로그아웃 설정
                .logout(AbstractHttpConfigurer::disable)

                // 4. CSRF 설정
                .csrf(AbstractHttpConfigurer::disable)

                // 5. 헤더 설정
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable) // X-Frame-Options 헤더 비활성화
                );

        return http.build();
    }



}
