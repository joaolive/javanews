package com.joaolive.javanews.profile.infrastructure.persistence;

import com.joaolive.javanews.profile.domain.Profile;

public class ProfileMapper {
	private ProfileMapper() {}
	public static ProfileEntity toEntity(Profile domain) {
		if (domain == null)
			return null;
		return new ProfileEntity(
			domain.getUserId(),
			domain.getFirstName().value(),
			domain.getLastName().value(),
			domain.getUsername().value(),
			domain.getBio().value(),
			domain.getAvatarKey(),
			domain.getCreatedAt(),
			domain.getUpdatedAt(),
			domain.getVersion()
		);
	}
	public static Profile toDomain(ProfileEntity entity) {
		if (entity == null)
			return null;
		return Profile.reconstitute(
			entity.getUserId(),
			entity.getFirstName(),
			entity.getLastName(),
			entity.getUsername(),
			entity.getBio(),
			entity.getAvatarKey(),
			entity.getCreatedAt(),
			entity.getUpdatedAt(),
			entity.getVersion()
		);
	}
}
