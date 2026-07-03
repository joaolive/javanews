package com.joaolive.javanews.user.application;

import com.joaolive.javanews.user.domain.User;
import com.joaolive.javanews.user.domain.UserRepository;
import com.joaolive.javanews.user.domain.exception.UserNotFoundException;
import com.joaolive.javanews.user.domain.valueobject.Email;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
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
}
