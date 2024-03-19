package com.eventty.authservice.api.dto.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OAuthUserCreateApiRequestDTO {
    private Long userId;
    private String name;
    private LocalDate birth;
    private String phone;
    private String picure;
}
