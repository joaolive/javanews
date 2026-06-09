package com.joaolive.javanews.user.infrastructure.web;

import com.joaolive.javanews.user.application.command.CreateUserCommand;

public record CreateUserRequest(
	String email,
	String username,
	String password,
	String firstName,
	String lastName,
	String bio,
	String avatarKey
) {
	public CreateUserCommand toCommand() {
		return new CreateUserCommand(
			this.email(),
			this.username(),
			this.password(),
			this.firstName(),
			this.lastName(),
			this.bio(),
			this.avatarKey()
		);
	}
}
