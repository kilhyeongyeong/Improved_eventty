package com.eventty.applyservice.application.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @ToString @Builder
@NoArgsConstructor @AllArgsConstructor
public class FindApplicantsListResposneDTO {
    private Long applyId;
    private String name;
    private String phone;
    private Long price;
    private LocalDateTime date;
    private String state;
}
