package com.eventty.authservice.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NaverUserInfoResponseDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    // 다른 필드들...
    @JsonProperty("birthday")
    private String birthday;

    @JsonProperty("birthyear")
    private String birthyear;

    @JsonProperty("profile_image")
    private String profileImage;

    @JsonProperty("mobile")
    private String mobile;
}
