package com.eventty.authservice.applicaiton.dto;

// CSRF Token은 넣지 않은 이유: JWT와 CSRF는 유저의 로그인 상태 확인에 초점이 있고, CSRF는 유효한 유저인지 재검증에 목적이 있어서,
// 대부분의 메서드에서 로직이 분리될 것이다. 따라서 세 객체를 하나의 DTO로 묶어 버리면 불필요한 결합성이 생겨서 활용하기 힘들다.
public record SessionTokensDTO(
        String accessToken,
        String refreshToken
){ }
