package com.joaolive.javanews.user.domain.valueobject;

public record Bio(String value) {
	public Bio {
		if (value != null && value.length() > 500)
			throw new IllegalArgumentException("Bio cannot exceed 500 characters");
	}
}
