package com.joaolive.javanews.post.domain.valueobject;

import com.joaolive.javanews.post.domain.exception.PostDomainValidationException;

public record Title(String value) {
	public Title {
		if (value == null || value.isBlank())
			throw new PostDomainValidationException("Title cannot be null or blank");
		if (value.length() > 200)
			throw new PostDomainValidationException("Title must be between 1 and 200 characters");
	}

	public static Title create(String value) {
		return new Title(value);
	}

	public static Title restore(String value) {
		return new Title(value);
	}
}