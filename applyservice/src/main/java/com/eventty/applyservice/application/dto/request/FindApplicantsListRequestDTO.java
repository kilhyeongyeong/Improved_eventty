package com.eventty.applyservice.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString @Builder
@AllArgsConstructor
public class FindApplicantsListRequestDTO {
    private Long eventId;
    private Long state;
    private Long applyId;
    private Long priceMin;
    private Long priceMax;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateMin;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateMax;
    private String phone;
    private Long order;
}
