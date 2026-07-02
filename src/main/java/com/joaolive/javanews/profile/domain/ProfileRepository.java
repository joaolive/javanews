package com.joaolive.javanews.profile.domain;

import com.joaolive.javanews.profile.domain.valueobject.Username;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository {Profile save(Profile profile);
	Optional<Profile> findById(UUID userId);
	Optional<Profile> findByUsername(String username);
	boolean existsByUsername(Username username);
	void flush();
}
