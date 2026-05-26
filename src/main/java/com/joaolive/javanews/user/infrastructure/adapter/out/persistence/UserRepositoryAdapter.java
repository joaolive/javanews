package com.joaolive.javanews.user.infrastructure.adapter.out.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.joaolive.javanews.user.application.port.out.UserRepository;
import com.joaolive.javanews.user.domain.model.User;
import com.joaolive.javanews.user.domain.valueobject.Email;
import com.joaolive.javanews.user.domain.valueobject.Username;

@Repository
public class UserRepositoryAdapter implements UserRepository {
	private final UserJpaRepository userRepository;

	public UserRepositoryAdapter(UserJpaRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void save(User user) {
		userRepository.save(UserMapper.toEntity(user));
	}

	@Override
	public Optional<User> findById(UUID id) {
		return userRepository.findById(id).map(UserMapper::toDomain);
	}

	@Override
	public Optional<User> findByEmail(Email email) {
		return userRepository.findByEmail(email.getValue()).map(UserMapper::toDomain);
	}

	@Override
	public boolean existsByUsername(Username username) {
		return (userRepository.existsByUsername(username.getValue()));
	}

	@Override
	public boolean existsByEmail(Email email) {
		return userRepository.existsByEmail(email.getValue());
	}
	
}
