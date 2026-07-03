package com.joaolive.javanews.user.infrastructure.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.joaolive.javanews.user.domain.Registration;
import com.joaolive.javanews.user.domain.RegistrationRepository;
import com.joaolive.javanews.user.domain.Registration.RegistrationStatus;
import com.joaolive.javanews.user.domain.valueobject.Email;

@Repository
public class RegistrationRepositoryAdapter implements RegistrationRepository {
	private final RegistrationJpaRepository jpaRepository;

	public RegistrationRepositoryAdapter(RegistrationJpaRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
	}

	@Override
	public Registration save(Registration domain) {
		RegistrationEntity entity = jpaRepository.save(RegistrationMapper.toEntity(domain));
		return RegistrationMapper.toDomain(entity);
	}

	@Override
	public Optional<Registration> findByEmailAndStatusPending(Email email) {
		return jpaRepository.findByEmailAndStatus(email.value(), RegistrationStatus.PENDING.name())
			.map(RegistrationMapper::toDomain);
	}
	
}
