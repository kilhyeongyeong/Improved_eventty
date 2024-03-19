package com.eventty.userservice.application.dto;

import com.eventty.userservice.domain.UserImageEntity;
import lombok.*;


@Getter @Setter @ToString @Builder
@AllArgsConstructor @NoArgsConstructor
public class UserImageDTO {
    private Long id;
    private Long userId;
    private String originalFileName;
    private String storedFilePath;
    private Long fileSize;

    public UserImageEntity toEntity(){
        return UserImageEntity
                .builder()
                .id(this.id)
                .userId(this.userId)
                .originalFileName(this.originalFileName)
                .storedFilePath(this.storedFilePath)
                .fileSize(this.fileSize)
                .build();
    }
}
