package com.joaolive.javanews.profile.domain.valueobject;

import com.joaolive.javanews.profile.domain.exception.ProfileDomainValidationException;

public record Bio(String value) {
	public static Bio create(String value) {
		if (value != null && value.length() > 160)
			throw new ProfileDomainValidationException("Bio cannot exceed 160 characters");
		return new Bio(value);
	}
	public static Bio restore(String value) {
		return new Bio(value);
	}
}
