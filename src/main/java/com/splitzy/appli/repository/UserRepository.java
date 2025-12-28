package com.splitzy.appli.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.splitzy.appli.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}
