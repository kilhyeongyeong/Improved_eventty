package com.eventty.authservice.domain.repository;

import com.eventty.authservice.domain.entity.OAuthUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OAuthUserRepository extends JpaRepository<OAuthUserEntity, Long> {
    Optional<OAuthUserEntity> findOAuthUserEntityBySocialNameAndClientId(String socialName, String clientId);
}
