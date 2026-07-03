package com.joaolive.javanews.user.domain.valueobject;

import java.security.SecureRandom;

import com.joaolive.javanews.user.domain.exception.InvalidVerificationCodeException;

public record VerificationCode(String value) {
private static final SecureRandom SECURE_RANDOM = new SecureRandom();
	public VerificationCode {
		if (value == null || !value.matches("^\\d{6}$")) {
			throw new InvalidVerificationCodeException("Must contain exactly 6 digits");
		}
	}
	public static VerificationCode generate() {
		String code = String.format("%06d", SECURE_RANDOM.nextInt(1000000));
		return new VerificationCode(code);
	}

	public static VerificationCode restore(String value) {
		return new VerificationCode(value);
	}
}
