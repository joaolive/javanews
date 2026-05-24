package com.joaolive.javanews.user.domain.ports.out;

import java.util.Optional;
import java.util.UUID;

import com.joaolive.javanews.user.domain.User;
import com.joaolive.javanews.user.domain.records.Username;

public interface UserRepository {
	void save(User user);
	Optional<User> findById(UUID id);
	boolean existsByUsername(Username username);
}
