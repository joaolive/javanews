package com.joaolive.javanews.user.application.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaolive.javanews.user.application.port.out.UserRepository;
import com.joaolive.javanews.user.domain.exception.ResourceAlreadyExistsException;
import com.joaolive.javanews.user.domain.model.User;

@Service
public class FindUserByIdUseCase {
	private final UserRepository userRepository;

	public FindUserByIdUseCase(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional(readOnly = true)
	public User execute(UUID id) {
		return (userRepository.findById(id)
			.orElseThrow(() -> new ResourceAlreadyExistsException("User with ID '" + id + "' not found")));
	}
}
