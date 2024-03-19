package com.eventty.userservice.application.dto.response;

import com.eventty.userservice.domain.UserEntity;
import com.eventty.userservice.domain.UserImageEntity;
import lombok.*;
import org.springframework.core.io.FileSystemResource;

import java.time.LocalDate;

@Setter @Getter @Builder @ToString
@AllArgsConstructor
public class UserFindByIdResponseDTO {
    private Long userId;
    private String name;                // 이름
    private String address;             // 주소
    private LocalDate birth;            // 생일
    private String phone;               // 유저 전화번호
    private Long imageId;               // user_image Table 고유 ID
    private String imagePath;           // 이미지 저장 경로
    private String originFileName;      // 이미지 원본 파일 명

    public UserFindByIdResponseDTO(){
        userId = 1L;
        name = "name";
        address = "address";
        birth = LocalDate.of(1999,1,1);
        phone = "010-1234-5678";
        imagePath = "user/20230919/1590341394708.png";
        originFileName = "pompompurin.png";
        imageId = 3L;
    }

    public UserFindByIdResponseDTO(UserEntity user, UserImageEntity userImage){
        this.userId = user.getUserId();
        this.name = user.getName();
        this.address = user.getAddress();
        this.birth = user.getBirth();
        this.phone = user.getPhone();
        this.imageId = userImage == null ? null : user.getImageId();
        this.imagePath = userImage == null ? null : userImage.getStoredFilePath();
        this.originFileName = userImage == null ? null : userImage.getOriginalFileName();
    }
}