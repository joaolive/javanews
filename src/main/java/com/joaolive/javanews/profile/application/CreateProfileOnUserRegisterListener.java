package com.joaolive.javanews.profile.application;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import com.joaolive.javanews.profile.domain.Profile;
import com.joaolive.javanews.profile.domain.ProfileRepository;
import com.joaolive.javanews.profile.domain.exception.ProfileConflictException;
import com.joaolive.javanews.profile.domain.valueobject.Bio;
import com.joaolive.javanews.profile.domain.valueobject.Name;
import com.joaolive.javanews.profile.domain.valueobject.Username;
import com.joaolive.javanews.user.UserRegisterEvent;

@Component
public class CreateProfileOnUserRegisterListener {
	private final ProfileRepository profileRepository;

	public CreateProfileOnUserRegisterListener(ProfileRepository profileRepository) {
		this.profileRepository = profileRepository;
	}

	@ApplicationModuleListener
	public void on(UserRegisterEvent event) {
		Profile profile = Profile.create(
			event.userId(),
			Name.create(event.firstName()),
			Name.create(event.lastName()),
			Username.create(event.username()),
			Bio.create(""),
			null
		);
		try {
			profileRepository.save(profile);
		} catch (DataIntegrityViolationException e) {
			throw new ProfileConflictException("Username is already in use");
		}
	}
}
