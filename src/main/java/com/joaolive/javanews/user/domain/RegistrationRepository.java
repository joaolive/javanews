package com.joaolive.javanews.user.domain;

import java.util.Optional;
import java.util.UUID;

import com.joaolive.javanews.user.domain.valueobject.Email;

public interface RegistrationRepository {
	Registration save(Registration entity);
	void deleteById(UUID id);
	Optional<Registration> findByEmailAndStatusPending(Email email);
}
