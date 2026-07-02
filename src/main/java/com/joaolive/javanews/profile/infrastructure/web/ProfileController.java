package com.joaolive.javanews.profile.infrastructure.web;

import com.joaolive.javanews.auth.CurrentUser;
import com.joaolive.javanews.auth.UserContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joaolive.javanews.profile.application.ProfileService;
import com.joaolive.javanews.profile.domain.Profile;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {
	private final ProfileService profileService;

	public ProfileController(ProfileService profileService) {
		this.profileService = profileService;
	}

	@GetMapping("/{username}")
	public ResponseEntity<ProfileResponse> findByUsername(@PathVariable String username) {
		Profile profile = profileService.findByUsername(username);
		return ResponseEntity.ok(ProfileResponse.from(profile));
	}

	@PutMapping("/me")
	public ResponseEntity<ProfileResponse> updateProfile(
			@CurrentUser UserContext current,
			@Valid @RequestBody UpdateProfileRequest request) {
		Profile profile = profileService.update(current.id(), request.toCommand());
		return ResponseEntity.ok(ProfileResponse.from(profile));
	}

	@PutMapping("me/username")
	public ResponseEntity<ProfileResponse> updateUsername(
			@CurrentUser UserContext current,
			@Valid @RequestBody UpdateUsernameRequest request) {
		Profile profile = profileService.updateUsername(current.id(), request.toCommand());
		return ResponseEntity.ok(ProfileResponse.from(profile));
	}
}
