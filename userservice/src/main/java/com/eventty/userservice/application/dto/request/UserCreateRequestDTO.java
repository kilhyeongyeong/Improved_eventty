package com.eventty.userservice.application.dto.request;

import com.eventty.userservice.domain.UserEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;

@Setter @Getter @Builder @ToString
public class UserCreateRequestDTO {
    @NotNull(message = "a null value")
    private Long userId;
    @NotBlank(message = "a null value or '' value or ' ' value")
    private String name;
    private String address;
    @Past
    private LocalDate birth;
    @NotBlank(message = "a null value or '' value or ' ' value")
    private String phone;

    public UserEntity toEntity() {
        return UserEntity
                .builder()
                .userId(this.userId)
                .name(this.name)
                .address(this.address)
                .birth(this.birth)
                .phone(this.phone)
                .build();
    }
}