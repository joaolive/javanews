package com.joaolive.javanews.user.infrastructure.web.response;

import java.time.Instant;
import java.util.UUID;

import com.joaolive.javanews.user.domain.User;

public record UserResponse(
	UUID id,
	String username,
	String avatarKey,
	String bio,
	Instant createdAt) {
	public static UserResponse from(User user) {
		return new UserResponse(
			user.getId(),
			user.getUsername().getValue(),
			user.getAvatarKey(),
			user.getBio().getValue(),
			user.getCreatedAt()
		);
	}
}
