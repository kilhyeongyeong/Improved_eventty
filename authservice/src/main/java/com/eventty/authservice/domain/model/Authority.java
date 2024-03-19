package com.eventty.authservice.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Authority {
    private String role;
    // private String permission;
}
