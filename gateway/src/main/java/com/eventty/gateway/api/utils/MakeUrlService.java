package com.eventty.gateway.api.utils;

import com.eventty.gateway.api.config.UrlProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class MakeUrlService {

    private final String GET_NEW_TOKENS_URL = "/api/newtokens";
    private final String AUTHENTICATE_URL = "/api/authenticate/user";
    private final UrlProperties urlProperties;

    public URI createNewTokenUri() { return URI.create(urlProperties.getAuthServer() + GET_NEW_TOKENS_URL); }
    public URI authenticateUri() { return URI.create(urlProperties.getAuthServer() + AUTHENTICATE_URL); }
}
