package com.joaolive.javanews.profile.domain.valueobject;

public record Username(String value) {
	public static Username create(String value) {
		if (value == null || value.isBlank())
			throw new IllegalArgumentException("Username cannot be null or blank");
		if (value.length() < 3 || value.length() > 30)
			throw new IllegalArgumentException("Username must be between 3 and 30 characters");
		if (!value.matches("^(?=.{3,30}$)(?!.*\\.\\.)(?!\\.)[a-zA-Z0-9._]+(?<!\\.)$"))
			throw new IllegalArgumentException("Username can only contain letters, numbers, dots and underscores");
		return new Username(value);
	}
	public static Username restore(String value) {
		return new Username(value);
	}
}
