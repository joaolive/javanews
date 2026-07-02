package com.joaolive.javanews.user.infrastructure.persistence;

import com.joaolive.javanews.user.domain.User;
import com.joaolive.javanews.user.domain.UserRepository;
import com.joaolive.javanews.user.domain.valueobject.Email;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

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
		return userRepository.findByEmail(email.value()).map(UserMapper::toDomain);
	}

	@Override
	public boolean existsByEmail(Email email) {
		return userRepository.existsByEmail(email.value());
	}

}
