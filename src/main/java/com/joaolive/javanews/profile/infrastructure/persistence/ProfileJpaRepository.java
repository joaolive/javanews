package com.joaolive.javanews.profile.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfileJpaRepository extends JpaRepository<ProfileEntity, UUID> {
	Optional<ProfileEntity> findByUsername(String username);
	boolean existsByUsername(String username);
}
