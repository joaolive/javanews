package com.joaolive.javanews.user.infrastructure.persistence;

import java.util.stream.Collectors;

import com.joaolive.javanews.user.domain.User;
import com.joaolive.javanews.user.domain.valueobject.Role;

public class UserMapper {
	public static UserEntity toEntity(User user) {
		if (user == null)
			return null;
		UserEntity entity = new UserEntity();
		entity.setId(user.getId());
		entity.setAvatarKey(user.getAvatarKey());
		entity.setCreatedAt(user.getCreatedAt());
		entity.setUpdatedAt(user.getUpdatedAt());
		entity.setPassword(user.getPassword());
		entity.setUsername(user.getUsername().getValue());
		entity.setEmail(user.getEmail().getValue());
		entity.setFirstName(user.getFirstName().getValue());
		entity.setLastName(user.getLastName().getValue());
		if (user.getBio() != null)
			entity.setBio(user.getBio().getValue());
		user.getRoles().forEach(x -> {
			entity.addRole(RoleEntity.valueOf(x.name()));
		});
		return entity;
	}

	public static User toDomain(UserEntity entity) {
		if (entity == null)
			return null;
		return User.reconstitute(
				entity.getId(),
				entity.getEmail(),
				entity.getUsername(),
				entity.getPassword(),
				entity.getFirstName(),
				entity.getLastName(),
				entity.getBio(),
				entity.getAvatarKey(),
				entity.getCreatedAt(),
				entity.getUpdatedAt(),
				entity.getRoles().stream().map(x -> Role.valueOf(x.name())).collect(Collectors.toSet())
			);
	}
}
