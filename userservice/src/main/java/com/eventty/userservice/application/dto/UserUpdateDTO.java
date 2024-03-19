package com.eventty.userservice.application.dto;

import com.eventty.userservice.application.dto.request.UserUpdateRequestDTO;
import com.eventty.userservice.domain.UserEntity;
import lombok.*;

import java.time.LocalDate;
import java.util.StringTokenizer;

@Setter @Getter @Builder @ToString
@AllArgsConstructor
public class UserUpdateDTO {

    private Long userId;
    private String name;
    private String phone;
    private LocalDate birth;
    private String address;
    private Long imageId;

    public UserUpdateDTO(UserEntity user){

        this.userId = user.getUserId();
        this.name = user.getName();
        this.phone = user.getPhone();
        this.birth = user.getBirth();
        this.address = user.getAddress();
        this.imageId = user.getImageId();
    }

    public UserEntity toEntity(UserUpdateRequestDTO dto, Long imageId){
        return UserEntity
                .builder()
                .userId(this.userId)
                .name(dto.getName() != null ? checkNull(dto.getName()) : this.name)
                .phone(dto.getPhone() != null ? checkNull(dto.getPhone()) : this.phone)
                .birth(dto.getBirth() != null ? StringToLocalDate(dto.getBirth()) : this.birth)
                .address(dto.getAddress() != null? checkNull(dto.getAddress()) : this.address)
                .imageId(imageId == 0 ? null : imageId)
                .build();
    }

    private String checkNull(String value){
        if("null".equalsIgnoreCase(value)) return "";
        return value;
    }

    private LocalDate StringToLocalDate(String birth){
        if("".equals(birth) || "null".equalsIgnoreCase(birth)){
            return null;
        }

        return LocalDate.parse(birth.substring(0,10));
    }
}