package com.joaolive.javanews.user.domain.valueobject;

public class Bio {
	private final String value;

	private Bio(String value) {
		this.value = value;
	}

	public static Bio create(String value) {
		if (value != null && value.length() > 500)
			throw new IllegalArgumentException("Bio cannot exceed 500 characters");
		return new Bio(value);
	}

	public static Bio restore(String value) {
		return new Bio(value);
	}

	public String getValue() {
		return value;
	}
}
