package com.eventty.businessservice.event.api.dto.response;

import lombok.*;

import java.time.LocalDate;

/**
 * User Server 와의 통신 결과로 받는 DTO (From Event Server, To Here)
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HostFindByIdResponseDTO {
    private String name;                // 이름
    private String phone;               // 유저 전화번호
}
