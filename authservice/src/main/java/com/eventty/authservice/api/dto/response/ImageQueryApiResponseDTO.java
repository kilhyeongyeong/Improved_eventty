package com.eventty.authservice.api.dto.response;

/*
 User Server로 받아올 DTO
 이미지 불러오기
 */

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ImageQueryApiResponseDTO {
    private String originFileName;      // 이미지 원본 이름
    private String imagePath;           // 이미지 경로 이름
}
