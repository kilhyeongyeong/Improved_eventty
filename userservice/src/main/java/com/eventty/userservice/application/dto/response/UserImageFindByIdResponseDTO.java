package com.eventty.userservice.application.dto.response;

import com.eventty.userservice.domain.UserImageEntity;
import lombok.*;

@Getter @Setter @ToString @Builder
@AllArgsConstructor
public class UserImageFindByIdResponseDTO {
    private String imagePath;           // 이미지 저장 경로
    private String originFileName;      // 이미지 원본 파일 명

    public UserImageFindByIdResponseDTO(){
        imagePath = "user/20230919/1590341394708.png";
        originFileName = "pompompurin.png";
    }

    public UserImageFindByIdResponseDTO(UserImageEntity userImage){
        imagePath = userImage == null ? null : userImage.getStoredFilePath();
        originFileName = userImage == null ? null : userImage.getOriginalFileName();
    }
}
