package com.joaolive.javanews.user.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaolive.javanews.user.application.port.out.UserRepository;
import com.joaolive.javanews.user.domain.valueobject.Username;

@Service
public class CheckUsernameAvailabilityUseCaseImpl implements CheckUsernameAvailabilityUseCase {
	private final UserRepository userRepository;

	public CheckUsernameAvailabilityUseCaseImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean execute(String rawUsername) {
		Username username = new Username(rawUsername);
		return (!userRepository.existsByUsername(username));
	}
}
