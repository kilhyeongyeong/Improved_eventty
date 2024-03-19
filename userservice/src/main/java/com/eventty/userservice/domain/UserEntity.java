package com.eventty.userservice.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity @Builder @Getter @ToString
@AllArgsConstructor @DynamicUpdate
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user")
public class UserEntity {
    @Id
    @NotNull
    private Long userId;                // auth Server에 저장 후 받을 PK값

    @NotNull
    private String name;                // 이름

    private String address;             // 주소

    private LocalDate birth;            // 생일

    private Long imageId;               // 유저 사진

    private String phone;               // 유저 전화번호

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createDate;   // 회원가입 일자

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updateDate;   // 회원 정보 수정 일자
}
