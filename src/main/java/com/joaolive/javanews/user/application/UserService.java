package com.joaolive.javanews.user.application;

import com.joaolive.javanews.user.UserRegisterEvent;
import com.joaolive.javanews.user.domain.User;
import com.joaolive.javanews.user.domain.UserRepository;
import com.joaolive.javanews.user.domain.exception.UserConflictException;
import com.joaolive.javanews.user.domain.exception.UserNotFoundException;
import com.joaolive.javanews.user.domain.valueobject.Email;

import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
	private final ApplicationEventPublisher publisher;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	public UserService(ApplicationEventPublisher publisher, PasswordEncoder passwordEncoder, UserRepository userRepository) {
		this.publisher = publisher;
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
	}

	@Transactional(readOnly = true)
	public User findById(UUID id) {
		return userRepository.findById(id)
			.orElseThrow(() -> new UserNotFoundException("User not found"));
	}

	@Transactional(readOnly = true)
	public User findByEmail(String email) {
		return userRepository.findByEmail(Email.create(email))
				.orElseThrow(() -> new UserNotFoundException("User not found"));
	}

	@Transactional
	public User register(RegisterUserCommand command) {
		Email email = Email.create(command.email());
		if (userRepository.existsByEmail(email))
			throw new UserConflictException("Email is already in use");
		String hash = passwordEncoder.encode(command.password());
		User user = User.create(email, hash);
		user = userRepository.save(user);
		publisher.publishEvent(new UserRegisterEvent(
			user.getId(),
			command.username(),
			command.firstName(),
			command.lastName()));
		return user;
	}
}
