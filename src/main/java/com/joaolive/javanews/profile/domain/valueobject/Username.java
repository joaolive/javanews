package com.joaolive.javanews.profile.domain.valueobject;

import java.util.Objects;

public class Username {
	private final String value;

	private Username(String value) {
		this.value = value;
	}

	public static Username create(String value) {
		Objects.requireNonNull(value, "Username cannot be null");
		if (value.isBlank())
			throw new IllegalArgumentException("Username cannot be blank");
		if (value.length() < 3 || value.length() > 30)
			throw new IllegalArgumentException("Username must be between 3 and 30 characters");
		if (!value.matches("^(?=.{3,30}$)(?!.*\\.\\.)(?!\\.)[a-zA-Z0-9._]+(?<!\\.)$"))
			throw new IllegalArgumentException("Username can only contain letters, numbers, dots and underscores");
		return new Username(value);
	}

	public static Username restore(String value) {
		return new Username(value);
	}

	public String getValue() {
		return value;
	}
}
