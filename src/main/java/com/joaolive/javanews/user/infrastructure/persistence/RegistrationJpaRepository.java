package com.joaolive.javanews.user.infrastructure.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationJpaRepository extends JpaRepository<RegistrationEntity, UUID> {
	Optional<RegistrationEntity> findByEmailAndStatus(String email, String status);
}
