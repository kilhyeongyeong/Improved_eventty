package com.eventty.businessservice.event.api.utils;

import com.eventty.businessservice.event.api.config.UrlProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class MakeUrlService {

    private final UrlProperties urlProperties;
    public URI queryHostInfo(Long hostId) {
        String QUERY_USER_INFO_API_PATH = "/api/host?hostId=" + hostId;
        return URI.create(urlProperties.getUserServer() + QUERY_USER_INFO_API_PATH);
    }

    public URI queryTicketCount(Long eventId) {
        String QUERY_TICKET_COUNT_API_PATH = "/api/applies/count?eventId=" + eventId;
        return URI.create(urlProperties.getApplyServer() + QUERY_TICKET_COUNT_API_PATH);
    }
}
