package com.joaolive.javanews.user.infrastructure.adapter.out.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
}
