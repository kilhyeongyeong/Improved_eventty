package com.eventty.userservice.application.dto.response;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class UserUpdateImageResponseDTO {
    private String imagePath;           // 이미지 저장 경로
    private String originFileName;      // 이미지 원본 파일 명
    private Long imageId;               // 이미지 고유 Id

    public UserUpdateImageResponseDTO(Long imageId){
        this.imageId = imageId;
    }
}
