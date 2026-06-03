package com.joaolive.javanews.user.domain.valueobject;

import com.joaolive.javanews.user.domain.exception.UserDomainValidationException;

public class Email {
	private final String value;

	private Email(String value) {
		this.value = value;
	}

	public static Email create(String value) {
		if (value == null || value.isBlank())
			throw new UserDomainValidationException("Email cannot be blank or blank");
		if (!value.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$"))
			throw new UserDomainValidationException("Invalid email format");
		return new Email(value);
	}

	public static Email restore(String value) {
		return new Email(value);
	}

	public String getValue() {
		return value;
	}
}
