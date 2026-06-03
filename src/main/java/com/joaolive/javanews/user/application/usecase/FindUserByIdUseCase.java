package com.joaolive.javanews.user.application.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaolive.javanews.user.domain.User;
import com.joaolive.javanews.user.domain.UserRepository;
import com.joaolive.javanews.user.domain.exception.UserNotFoundException;

@Service
public class FindUserByIdUseCase {
	private final UserRepository userRepository;

	public FindUserByIdUseCase(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional(readOnly = true)
	public User execute(UUID id) {
		return (userRepository.findById(id)
			.orElseThrow(() -> new UserNotFoundException("User with ID '" + id + "' not found")));
	}
}
