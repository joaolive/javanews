package com.joaolive.javanews.user.domain.valueobject;

import java.util.Objects;

public class Name {
	private final String value;

	private Name(String value) {
		this.value = value;
	}
	public static Name create(String value) {
		Objects.requireNonNull(value, "Name cannot be null");
		if (value.isBlank())
			throw new IllegalArgumentException("Name cannot be null");
		return new Name(value);
	}

	public static Name restore(String value) {
		return new Name(value);
	}
}
