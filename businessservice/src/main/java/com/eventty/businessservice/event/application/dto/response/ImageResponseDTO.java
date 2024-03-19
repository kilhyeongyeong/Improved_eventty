package com.eventty.businessservice.event.application.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class ImageResponseDTO {
    private Long imageId;
    private String imagePathFromStorage;
    private String imageOriginalFileName;
}
