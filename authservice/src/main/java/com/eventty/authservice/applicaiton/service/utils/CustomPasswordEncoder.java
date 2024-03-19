package com.eventty.authservice.applicaiton.service.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomPasswordEncoder {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    CustomPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    public boolean match(String rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }

    public String encodePassword(String rawPassword) {
        return bCryptPasswordEncoder.encode(rawPassword);
    }
}
