package com.eventty.authservice.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eventty.authservice.domain.entity.AuthUserEntity;

import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUserEntity, Long> {
    Optional<AuthUserEntity>findByEmail(String email);
}
