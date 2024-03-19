package com.eventty.userservice.application.dto.request;

import lombok.*;

@Setter @Getter @Builder @ToString
@NoArgsConstructor @AllArgsConstructor
public class UserUpdateRequestDTO {
    private String name;
    private String phone;
    private String birth;
    private String address;

    public UserUpdateRequestDTO(UserUpdateRequestDTO user){
        name = "null".equalsIgnoreCase(user.getName()) || null == user.getName() ? "" : user.getName();
        phone = "null".equalsIgnoreCase(user.getPhone()) || null == user.getPhone() ? "" : user.getPhone();
        birth = "null".equalsIgnoreCase(user.getBirth()) || null == user.getBirth() ? "" : user.getBirth();
        address = "null".equalsIgnoreCase(user.getAddress()) || null == user.getAddress() ? "" : user.getAddress();
    }
}
