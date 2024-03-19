package com.eventty.businessservice.event.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("server.base.url")
public class UrlProperties {
    private String userServer;
    private String applyServer;
}
