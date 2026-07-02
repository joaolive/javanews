package com.joaolive.javanews.profile.application;

import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaolive.javanews.profile.ProfileUsernameChangedEvent;
import com.joaolive.javanews.profile.domain.Profile;
import com.joaolive.javanews.profile.domain.ProfileRepository;
import com.joaolive.javanews.profile.domain.exception.ProfileConflictException;
import com.joaolive.javanews.profile.domain.exception.ProfileNotFoundException;
import com.joaolive.javanews.profile.domain.valueobject.Bio;
import com.joaolive.javanews.profile.domain.valueobject.Name;
import com.joaolive.javanews.profile.domain.valueobject.Username;

@Service
public class ProfileService {
	private final ProfileRepository profileRepository;
	private final ApplicationEventPublisher eventPublisher;

	public ProfileService(ProfileRepository profileRepository, ApplicationEventPublisher eventPublisher) {
		this.profileRepository = profileRepository;
		this.eventPublisher = eventPublisher;
	}

	@Transactional(readOnly = true)
	public Profile findById(UUID id) {
		return profileRepository.findById(id)
				.orElseThrow(() -> new ProfileNotFoundException("Profile not found"));
	}

	@Transactional(readOnly = true)
	public Profile findByUsername(String username) {
		return profileRepository.findByUsername(username)
				.orElseThrow(() -> new ProfileNotFoundException("Profile not found"));
	}

		@Transactional
	public Profile update(UUID userId, UpdateProfileCommand command) {
		Profile profile = findById(userId);
		profile.update(
			Name.create(command.firstName()),
			Name.create(command.lastName()),
			Bio.create(command.bio()),
			command.avatarKey()
		);
		return profileRepository.save(profile);
	}

	@Transactional
	public Profile updateUsername(UUID userId, UpdateUsernameCommand command) {
		Profile profile = findById(userId);
		Username newUsername = Username.create(command.username());
		if (profile.getUsername().equals(newUsername))
			return profile;
		profile.updateUsername(newUsername);
		Profile saved;
		try {
			saved = profileRepository.save(profile);
			profileRepository.flush(); 
		} catch (DataIntegrityViolationException e) {
			throw new ProfileConflictException("Username is already in use");
		} catch (ObjectOptimisticLockingFailureException e) {
			throw new ProfileConflictException("Profile was modified concurrently, please try again");
		}
		eventPublisher.publishEvent(
			new ProfileUsernameChangedEvent(saved.getUserId(), saved.getUsername().value()));
		return saved;
	}
}
