package com.eventty.authservice.api.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserIdFindApiRequestDTO {
    private String name;
    private String phone;
}
