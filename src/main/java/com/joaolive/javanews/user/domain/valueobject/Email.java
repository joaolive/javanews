package com.joaolive.javanews.user.domain.valueobject;

import com.joaolive.javanews.user.domain.exception.UserDomainValidationException;

public record Email(String value) {
	public static Email create(String value) {
		if (value == null || value.isBlank()) {
			throw new UserDomainValidationException("Email cannot be null or blank");
		}
		if (!value.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
			throw new UserDomainValidationException("Invalid email format");
		}
		return new Email(value);
	}

	public static Email restore(String value) {
		return new Email(value);
	}
}