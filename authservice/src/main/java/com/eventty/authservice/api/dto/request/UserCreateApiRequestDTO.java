package com.eventty.authservice.api.dto.request;

import lombok.*;

import java.time.LocalDate;

/*
 User Server로 보낼 DTO
 회원 가입
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserCreateApiRequestDTO {
    private Long userId;
    private String name;
    private String address;
    private LocalDate birth;
    private String phone;
}
