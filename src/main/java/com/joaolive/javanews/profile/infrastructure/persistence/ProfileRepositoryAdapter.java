package com.joaolive.javanews.profile.infrastructure.persistence;

import com.joaolive.javanews.profile.domain.Profile;
import com.joaolive.javanews.profile.domain.ProfileRepository;
import com.joaolive.javanews.profile.domain.valueobject.Username;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ProfileRepositoryAdapter implements ProfileRepository {
	private final ProfileJpaRepository profileRepository;

	public ProfileRepositoryAdapter(ProfileJpaRepository profileRepository) {
		this.profileRepository = profileRepository;
	}

	@Override
	public Profile save(Profile profile) {
		ProfileEntity entity = ProfileMapper.toEntity(profile);
		return ProfileMapper.toDomain(profileRepository.save(entity));
	}

	@Override
	public Optional<Profile> findById(UUID userId) {
		return profileRepository.findById(userId).map(ProfileMapper::toDomain);
	}

	@Override
	public Optional<Profile> findByUsername(String username) {
		return profileRepository.findByUsername(username).map(ProfileMapper::toDomain);
	}

	@Override
	public boolean existsByUsername(Username username) {
		return profileRepository.existsByUsername(username.value());
	}

	@Override
	public void flush() {
		this.profileRepository.flush();
	}

	@Override
	public boolean existsByUserId(UUID userId) {
		return profileRepository.existsById(userId);
	}
}
