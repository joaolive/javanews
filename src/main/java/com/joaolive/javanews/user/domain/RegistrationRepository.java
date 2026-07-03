package com.joaolive.javanews.user.domain;

import java.util.Optional;

import com.joaolive.javanews.user.domain.valueobject.Email;

public interface RegistrationRepository {
	Registration save(Registration entity);
	Optional<Registration> findByEmailAndStatusPending(Email email);
}
