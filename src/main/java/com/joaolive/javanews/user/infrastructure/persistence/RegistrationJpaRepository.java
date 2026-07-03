package com.joaolive.javanews.user.infrastructure.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joaolive.javanews.user.domain.Registration.RegistrationStatus;

public interface RegistrationJpaRepository extends JpaRepository<RegistrationEntity, UUID> {
	Optional<RegistrationEntity> findByEmailAndStatus(String email, RegistrationStatus status);
}
