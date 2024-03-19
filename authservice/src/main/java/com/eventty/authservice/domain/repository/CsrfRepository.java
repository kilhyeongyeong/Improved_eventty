package com.eventty.authservice.domain.repository;

import com.eventty.authservice.domain.entity.CsrfTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CsrfRepository extends JpaRepository<CsrfTokenEntity, Long> {
    Optional<CsrfTokenEntity> findByUserId(Long userId);
}
