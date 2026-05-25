package com.joaolive.javanews.user.infrastructure.adapter.out.persistence;

import com.joaolive.javanews.user.domain.model.User;

public class UserMapper {
	public static UserEntity toEntity(User user) {
		if (user == null)
			return null;
		UserEntity entity = new UserEntity();
		entity.setId(user.getId());
		entity.setAvatarKey(user.getAvatarKey());
		entity.setCreatedAt(user.getCreatedAt());
		entity.setUpdatedAt(user.getUpdatedAt());
		entity.setUsername(user.getUsername().getValue());
		entity.setFirstName(user.getFirstName().getValue());
		entity.setLastName(user.getLastName().getValue());
		if (user.getBio() != null)
			entity.setBio(user.getBio().getValue());
		return entity;
	}

	public static User toDomain(UserEntity entity) {
		if (entity == null)
			return null;
		return User.reconstitute(
				entity.getId(),
				entity.getEmail(),
				entity.getUsername(),
				entity.getFirstName(),
				entity.getLastName(),
				entity.getBio(),
				entity.getAvatarKey(),
				entity.getCreatedAt(),
				entity.getUpdatedAt());
	}
}
