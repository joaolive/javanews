package com.joaolive.javanews.user.domain;

import com.joaolive.javanews.user.domain.valueobject.Email;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
	User save(User user);
	Optional<User> findById(UUID id);
	Optional<User> findByEmail(Email email);
	boolean existsByEmail(Email email);
}
