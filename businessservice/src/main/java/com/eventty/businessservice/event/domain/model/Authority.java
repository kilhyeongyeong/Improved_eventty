package com.eventty.businessservice.event.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Authority {

    // 현재는 역할만 저장하고 있음
    String role;
    // String permission;
}
