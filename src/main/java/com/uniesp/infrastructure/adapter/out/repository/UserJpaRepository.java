package com.uniesp.infrastructure.adapter.out.entity;

import com.uniesp.infrastructure.adapter.out.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Interface Spring Data — só conhece UserEntity, nunca o domínio
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}