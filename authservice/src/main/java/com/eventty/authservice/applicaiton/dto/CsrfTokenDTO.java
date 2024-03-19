package com.eventty.authservice.applicaiton.dto;

public record CsrfTokenDTO (Long userId, String value) { }
