package com.joaolive.javanews.user.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaolive.javanews.user.domain.UserRepository;
import com.joaolive.javanews.user.domain.valueobject.Username;

@Service
public class CheckUsernameAvailabilityUseCase {
	private final UserRepository userRepository;

	public CheckUsernameAvailabilityUseCase(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional(readOnly = true)
	public boolean execute(String rawUsername) {
		Username username = Username.create(rawUsername);
		return (!userRepository.existsByUsername(username));
	}
}
