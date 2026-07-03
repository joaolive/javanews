package com.joaolive.javanews.user.infrastructure.persistence;

import com.joaolive.javanews.user.domain.Registration;

public class RegistrationMapper {
	private RegistrationMapper() {}
	
	public static RegistrationEntity toEntity(Registration domain) {
		if (domain == null)
			return null;
		return new RegistrationEntity(
			domain.getId(),
			domain.getEmail().value(),
			domain.getPassword(),
			domain.getUsername(),
			domain.getFirstName(),
			domain.getLastName(),
			domain.getVerificationCode(),
			domain.getStatus(),
			domain.getCreatedAt(),
			domain.getExpiresAt(),
			domain.getFailedAttempts()
		);
	}

	public static Registration toDomain(RegistrationEntity entity) {
		if (entity == null)
			return null;
		return Registration.reconstitute(
			entity.getId(),
			entity.getEmail(),
			entity.getPassword(),
			entity.getUsername(),
			entity.getFirstName(),
			entity.getLastName(),
			entity.getVerificationCode(),
			entity.getStatus(),
			entity.getCreatedAt(),
			entity.getExpiresAt(),
			entity.getFailedAttempts()
		);
	}
}
