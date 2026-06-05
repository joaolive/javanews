package com.joaolive.javanews.user.application.usecase;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.joaolive.javanews.user.UserInternalApi;
import com.joaolive.javanews.user.domain.UserRepository;

@Service
public class ProvideUserInternalApi implements UserInternalApi {
	private final UserRepository userRepository;

	public ProvideUserInternalApi(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Optional<UUID> findIdByUsername(String username) {
		return userRepository.findByUsername(username).map(x -> x.getId());
	}

}
