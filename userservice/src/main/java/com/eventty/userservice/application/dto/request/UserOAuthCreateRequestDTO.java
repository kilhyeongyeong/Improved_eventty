package com.eventty.userservice.application.dto.request;

import com.eventty.userservice.domain.UserEntity;
import com.eventty.userservice.domain.UserImageEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @ToString @Builder
@AllArgsConstructor @NoArgsConstructor
public class UserOAuthCreateRequestDTO {
    @NotNull
    private Long userId;
    @NotBlank(message = "a null value or '' value or ' ' value")
    private String name;
    private LocalDate birth;
    private String phone;
    private String picture;

    public UserImageEntity toUserImageEntity(){
        return UserImageEntity
                .builder()
                .userId(this.userId)
                .originalFileName("")
                .fileSize(null)
                .storedFilePath(this.picture)
                .build();
    }

    public UserEntity toUserEntity(Long imageId){
        return UserEntity
                .builder()
                .userId(this.userId)
                .imageId(imageId)
                .name("".equalsIgnoreCase(this.name.trim()) ? null : this.name)
                .phone("".equalsIgnoreCase(this.phone.trim()) ? null : this.phone)
                .birth(this.birth)
                .build();
    }
}
