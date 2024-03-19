package com.eventty.authservice.applicaiton.dto;

public record TokenParsingDTO (
        Long userId,
        boolean needsUpdate
){ }
