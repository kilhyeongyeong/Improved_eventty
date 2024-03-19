package com.eventty.authservice.applicaiton.service.subservices;

import com.eventty.authservice.applicaiton.service.subservices.factory.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

@Lazy
@Service
public class OAuthServiceFactory {

    private final WebApplicationContext context;

    @Autowired
    public OAuthServiceFactory(WebApplicationContext context) {
        this.context = context;
    }

    public OAuthService getOAuthService(String serviceName) {
        return (OAuthService) context.getBean(serviceName + "OAuthService");
    }
}

