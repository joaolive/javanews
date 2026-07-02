package com.joaolive.javanews.profile.domain.valueobject;

import com.joaolive.javanews.profile.domain.exception.ProfileDomainValidationException;

public record Name(String value) {
	public static Name create(String value) {
		if (value == null || value.isBlank())
			throw new ProfileDomainValidationException("Name cannot be null or blank");
		return new Name(value);	
	}
	public static Name restore(String value) {
		return new Name(value);
	}
}
