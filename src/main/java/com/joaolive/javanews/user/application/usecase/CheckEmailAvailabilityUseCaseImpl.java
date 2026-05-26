package com.joaolive.javanews.user.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaolive.javanews.user.application.port.out.UserRepository;
import com.joaolive.javanews.user.domain.valueobject.Email;

@Service
public class CheckEmailAvailabilityUseCaseImpl implements CheckEmailAvailabilityUseCase {
	private final UserRepository userRepository;

	public CheckEmailAvailabilityUseCaseImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean execute(String rawEmail) {
		Email email = Email.create(rawEmail);
		return (!userRepository.existsByEmail(email));
	}
}
