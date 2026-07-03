package com.joaolive.javanews.user.infrastructure.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record VerifyEmailRequest(
	@NotBlank(message = "Email cannot be null or blank")
	@Pattern(regexp = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$", message = "Invalid email format")
	String email,
	@Pattern(regexp = "^\\d{6}$", message = "Must contain exactly 6 digits")
	String code
) {
	
}
