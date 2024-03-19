package com.eventty.userservice.application.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserImageUpdateRequestDTO {
    private String imageId;
    private MultipartFile image;
    private String isUpdate;

    public UserImageUpdateRequestDTO(UserImageUpdateRequestDTO userImage){
        imageId = "null".equalsIgnoreCase(userImage.getImageId()) || null == userImage.getImageId() ? "" : userImage.getImageId();
        isUpdate = "null".equalsIgnoreCase(userImage.getIsUpdate()) || null == userImage.getIsUpdate() ? "" : userImage.getIsUpdate();
        image = userImage.image;
    }
}
