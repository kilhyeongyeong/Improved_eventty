package com.eventty.authservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "auth_authority")
public class AuthorityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;    // 권한 이름(역할)

    @JoinColumn(name = "auth_user_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private AuthUserEntity authUserEntity;
}
