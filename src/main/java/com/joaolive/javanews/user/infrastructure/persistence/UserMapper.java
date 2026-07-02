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
		entity.setCreatedAt(user.getCreatedAt());
		entity.setUpdatedAt(user.getUpdatedAt());
		entity.setPassword(user.getPassword());
		entity.setEmail(user.getEmail().value());
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
				entity.getPassword(),
				entity.getCreatedAt(),
				entity.getUpdatedAt(),
				entity.getRoles().stream().map(x -> Role.valueOf(x.name())).collect(Collectors.toSet())
			);
	}
}
