package com.eventty.userservice.application.dto.response;

import com.eventty.userservice.domain.UserEntity;
import lombok.*;

@Getter @Setter @ToString @Builder
@AllArgsConstructor
public class HostFindByIdResposneDTO {
    private String name;
    private String phone;

    public HostFindByIdResposneDTO(){
        name = "홍길동";
        phone = "010-9999-8888";
    }

    public HostFindByIdResposneDTO(UserEntity user){
        name = user.getName();
        phone = user.getPhone();
    }
}
