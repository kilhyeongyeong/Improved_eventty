package com.eventty.authservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "auth_social_user")
public class OAuthUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    Long id;

    @Column(nullable = false)
    Long userId;

    @Column(nullable = false)
    String clientId;

    @Column(nullable = false)
    String socialName;
}
