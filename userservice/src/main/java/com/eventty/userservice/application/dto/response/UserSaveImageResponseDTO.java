package com.eventty.userservice.application.dto.response;

import lombok.*;

@Getter @Setter @ToString @Builder
@AllArgsConstructor @NoArgsConstructor
public class UserSaveImageResponseDTO {
    private String imagePath;           // 이미지 저장 경로
    private String originFileName;      // 이미지 원본 파일 명
}
