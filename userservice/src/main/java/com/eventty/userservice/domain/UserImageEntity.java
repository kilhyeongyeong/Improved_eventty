package com.eventty.userservice.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity @Builder @Getter @ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "userImage")
public class UserImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long userId;

    private String originalFileName;

    private String storedFilePath;

    private Long fileSize;
}
