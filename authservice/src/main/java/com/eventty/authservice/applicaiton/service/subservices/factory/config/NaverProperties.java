package com.eventty.authservice.applicaiton.service.subservices.factory.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("oauth2.naver")
public class NaverProperties {
    String client_id;
    String client_secret;
    String redirect_url;
}
