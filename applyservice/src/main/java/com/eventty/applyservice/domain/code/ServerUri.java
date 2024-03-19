package com.eventty.applyservice.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Getter
@Setter
@Component
@RequiredArgsConstructor
@ConfigurationProperties("server.base.url")
public class ServerUri {
    private String eventServer;

    private final String GET_EVENT_TICKET_INFO = "/api/events";
}
