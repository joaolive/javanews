package com.joaolive.javanews.user.domain;

import java.util.Optional;
import java.util.UUID;

import com.joaolive.javanews.profile.domain.valueobject.Username;
import com.joaolive.javanews.user.domain.valueobject.Email;

public interface UserRepository {
	User save(User user);
	Optional<User> findById(UUID id);
	Optional<User> findByEmail(Email email);
	boolean existsByUsername(Username username);
	boolean existsByEmail(Email email);
	Optional<User> findByUsername(String username);
}
