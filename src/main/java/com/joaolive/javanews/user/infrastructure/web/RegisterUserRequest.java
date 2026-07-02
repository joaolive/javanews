package com.joaolive.javanews.user.infrastructure.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterUserRequest(
	@NotBlank(message = "First name cannot be null or blank")
	String firstName,
	@NotBlank(message = "Last name cannot be null or blank")
	String lastName,
	@Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
	@Pattern(regexp = "^(?=.{3,30}$)(?!.*\\.\\.)(?!\\.)[a-zA-Z0-9._]+(?<!\\.)$", message = "Invalid username format")
	String username,
	@NotBlank(message = "Email cannot be null or blank")
	@Pattern(regexp = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$", message = "Invalid email format")
	String email,
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,72}$", message = "Invalid password format")
	String password
) {}
