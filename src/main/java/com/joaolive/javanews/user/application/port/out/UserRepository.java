package com.joaolive.javanews.user.application.port.out;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;

import com.joaolive.javanews.user.domain.model.User;
import com.joaolive.javanews.user.domain.valueobject.Email;
import com.joaolive.javanews.user.domain.valueobject.Username;

public interface UserRepository {
	void save(User user);
	Optional<User> findById(UUID id);
	boolean existsByUsername(Username username);
	boolean existsByEmail(Email email);
}
