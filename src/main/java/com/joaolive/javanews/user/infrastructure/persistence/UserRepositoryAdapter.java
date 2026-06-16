package com.joaolive.javanews.user.infrastructure.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.joaolive.javanews.profile.domain.valueobject.Username;
import com.joaolive.javanews.user.domain.User;
import com.joaolive.javanews.user.domain.UserRepository;
import com.joaolive.javanews.user.domain.valueobject.Email;

@Repository
public class UserRepositoryAdapter implements UserRepository {
	private final UserJpaRepository userRepository;

	public UserRepositoryAdapter(UserJpaRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User save(User user) {
		UserEntity entity = userRepository.save(UserMapper.toEntity(user));
		return UserMapper.toDomain(entity);
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

	@Override
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username).map(UserMapper::toDomain);
	}
	
}
