package com.eventty.authservice.domain.entity;

import lombok.*;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "auth_user")
public class AuthUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;                    // PK값

    @Column(nullable = false, unique = true)
    private String email;               // Email

    private String password;            // 암호화된 Password

    @ColumnDefault("false")
    @Column(name = "is_delete", nullable = false)
    private boolean isDelete;

    private LocalDateTime deleteDate;

    // @JsonManagedReference
    @OneToMany(mappedBy = "authUserEntity", cascade = CascadeType.PERSIST)
    private List<AuthorityEntity> authorities;
}
