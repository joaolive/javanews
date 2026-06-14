package com.joaolive.javanews.user.application;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaolive.javanews.user.domain.User;
import com.joaolive.javanews.user.domain.UserRepository;
import com.joaolive.javanews.user.domain.exception.UserNotFoundException;
import com.joaolive.javanews.user.domain.valueobject.Email;
import com.joaolive.javanews.user.domain.valueobject.Username;

@Service
public class UserQueryService {
	private final UserRepository userRepository;

	public UserQueryService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional(readOnly = true)
	public User findById(UUID id) {
		return (userRepository.findById(id)
			.orElseThrow(() -> new UserNotFoundException("User not found")));
	}

	@Transactional(readOnly = true)
	public boolean checkEmailAvailability(String rawEmail) {
		Email email = Email.create(rawEmail);
		return (!userRepository.existsByEmail(email));
	}

	@Transactional(readOnly = true)
	public boolean checkUsernameAvailability(String rawUsername) {
		Username username = Username.create(rawUsername);
		return (!userRepository.existsByUsername(username));
	}

}
