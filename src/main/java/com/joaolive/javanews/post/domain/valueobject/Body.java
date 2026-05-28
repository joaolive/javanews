package com.joaolive.javanews.post.domain.valueobject;

import com.joaolive.javanews.post.domain.exception.PostDomainValidationException;

public record Body(String value) {
	public Body {
		if (value == null || value.isBlank())
			throw new PostDomainValidationException("Body cannot be null or blank");
		if (value.length() > 20000)
			throw new PostDomainValidationException("Body must be between 1 and 20000 characters");
	}

	public static Body create(String value) {
		return new Body(value);
	}

	public static Body restore(String value) {
		return new Body(value);
	}
}
