package com.joaolive.javanews.profile.infrastructure.web;

import com.joaolive.javanews.profile.application.UpdateUsernameCommand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUsernameRequest(
	@NotBlank(message = "Username cannot be null or blank")
	@Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
	String username
) {
	public UpdateUsernameCommand toCommand() {
		return new UpdateUsernameCommand(this.username());
	}
}
