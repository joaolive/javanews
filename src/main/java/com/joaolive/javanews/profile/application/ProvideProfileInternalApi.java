package com.joaolive.javanews.profile.application;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.joaolive.javanews.profile.ProfileInternalApi;
import com.joaolive.javanews.profile.domain.ProfileRepository;

@Component
class ProvideProfileInternalApi implements ProfileInternalApi {
	private final ProfileRepository profileRepository;

	public ProvideProfileInternalApi(ProfileRepository profileRepository) {
		this.profileRepository = profileRepository;
	}

	@Override
	public Optional<UUID> findIdByUsername(String username) {
		return profileRepository.findByUsername(username)
			.map(profile -> profile.getUserId());
	}
}
