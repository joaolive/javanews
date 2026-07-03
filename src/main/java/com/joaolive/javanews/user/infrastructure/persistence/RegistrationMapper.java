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
			domain.getVerificationCode().value(),
			domain.getStatus(),
			domain.getCreatedAt(),
			domain.getExpiresAt(),
			domain.getFailedAttempts(),
			domain.getPayload()
		);
	}

	public static Registration toDomain(RegistrationEntity entity) {
		if (entity == null)
			return null;
		return Registration.reconstitute(
			entity.getId(),
			entity.getEmail(),
			entity.getVerificationCode(),
			entity.getStatus(),
			entity.getCreatedAt(),
			entity.getExpiresAt(),
			entity.getFailedAttempts(),
			entity.getPayload()
		);
	}
}
