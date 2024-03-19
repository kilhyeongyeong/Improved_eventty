package com.eventty.userservice.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserJPARepository extends JpaRepository<UserEntity, Long>{
    public List<UserEntity> findByNameAndPhone(String name, String phone);
}
