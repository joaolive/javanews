package com.joaolive.javanews.profile.infrastructure.web;

import java.time.Instant;

import com.joaolive.javanews.profile.domain.Profile;

public record ProfileResponse(
	String firstName,
	String lastName,
	String username,
	String bio,
	String avatarKey,
	Instant createdAt,
	Instant updatedAt
) {
	public static ProfileResponse from(Profile profile) {
		return new ProfileResponse(
			profile.getFirstName().value(),
			profile.getLastName().value(),
			profile.getUsername().value(),
			profile.getBio().value(),
			profile.getAvatarKey(),
			profile.getCreatedAt(),
			profile.getUpdatedAt());
	}
}
