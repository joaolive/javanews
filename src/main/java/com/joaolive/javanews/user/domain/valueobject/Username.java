package com.joaolive.javanews.user.domain.valueobject;

import java.util.Objects;

public record Username(String value) {
	public Username {
		Objects.requireNonNull(value, "Username cannot be null");
		if (value.isBlank())
			throw new IllegalArgumentException("Username cannot be blank");
		if (value.length() < 3 || value.length() > 30)
			throw new IllegalArgumentException("Username must be between 3 and 30 characters");
		if (!value.matches("^(?=.{3,30}$)(?!.*\\.\\.)(?!\\.)[a-zA-Z0-9._]+(?<!\\.)$"))
			throw new IllegalArgumentException("Username can only contain letters, numbers, dots and underscores");
	}
}
