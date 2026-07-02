package com.joaolive.javanews.profile.infrastructure.web;

import com.joaolive.javanews.profile.application.UpdateProfileCommand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateProfileRequest(
	@NotBlank(message = "First name cannot be null or blank")
	String firstName,
	@NotBlank(message = "Last name cannot be null or blank")
	String lastName,
	@Size(min = 0, max = 160, message = "Body must not exceed 160 characters")
	String bio,
	String avatarKey
) {
	public UpdateProfileCommand toCommand() {
		return new UpdateProfileCommand(
			this.firstName(),
			this.lastName(),
			this.bio(),
			this.avatarKey()
		);
	}
}
